/**
 * Created by Nettle on 2016/11/25.
 */

agbotApp.directive('attackGraph', [function () {
    return {
        restrict: 'E',
        replace: true,
        transclude: true,
        scope: {
            nodes: '=nodes',
            links: '=links',
            paths: '=paths'
        },
        templateUrl: 'html/algorithms/attack_graph_template',
        link: function (scope, element, attrs) {
            var height = window.innerHeight - 51;
            var width  = window.innerWidth -  256;

            scope.r = 30;
            scope.gap = 20;
            scope.selectedPathId = -1;

            scope.ag = d3.select('#attack-graph');
            scope.svg = scope.ag.append('g');

            scope.ag.call(d3.zoom().scaleExtent([0.3, 3]).on("zoom", zoomed)).on("dblclick.zoom", null);

            function scaleTo(x, y, k) {
                scope.svg.attr("transform",
                    'translate('+(x+scope.graphOffsetLeft*k)+' '+(y+scope.graphOffsetTop*k)+')' + "scale(" + (k*scope.graphRatio) + ")");
            }

            function zoomed() {
                scaleTo(d3.event.transform.x,d3.event.transform.y,d3.event.transform.k);
            }

            scope.$watchGroup(['nodes','links','paths'], function() {
                if (scope.nodes && scope.links)
                    scope.drawGraph();
            });

            scope.showPath = function (id) {
                scope.selectedPathId = id;
                if (id != -1) scope.drawPath(scope.paths[id]);
                else {
                    scope.link.classed("light", false);
                    scope.node.classed("light", false);
                }
            };

            scope.drawGraph = function () {
                // calculate coordinate
                scope.nodes[0].lv = 1;
                scope.nodes[0].offset = 0;

                var seq = [], num = [0];
                for (var i in scope.nodes) num.push(0);
                num[1] = 1;
                seq.push(1);

                for (var i = 0; i < seq.length; ++i) {
                    var v = seq[i];
                    for (var j = 0; j < scope.links.length; ++j)
                        if (scope.links[j].target == v && !scope.nodes[scope.links[j].source - 1].lv) {
                            scope.nodes[scope.links[j].source - 1].lv = scope.nodes[v - 1].lv + 1;
                            scope.nodes[scope.links[j].source - 1].offset = num[scope.nodes[scope.links[j].source - 1].lv]++;
                            seq.push(scope.links[j].source);
                        }
                }

                var maxSize = 0, maxLv = 0;

                for (var i in num)
                    if (num[i] > maxSize) maxSize = num[i];

                maxLv = num.filter(function (d) {
                    return d > 0;
                }).length;

                scope.nodes.forEach(function (node) {
                    var offset = (maxSize - num[ node.lv ]) * (scope.r*2 + scope.gap) / 2;
                    node.y = (maxLv - node.lv) * (scope.r*2+scope.gap*2)+scope.r;
                    node.x = offset + (node.offset)*(scope.r*2+scope.gap) + scope.r;
                });

                // important nodes
                if (scope.paths) {
                    scope.paths.forEach(function (path) {
                        path.forEach(function (d) {
                            if (!scope.nodes[d - 1].inPathTimes)
                                scope.nodes[d - 1].inPathTimes = 0;
                            scope.nodes[d - 1].inPathTimes++;
                        })
                    });
                }

                scope.getColor = function (d) {
                    if (d.id == 1) return '#FF3030';
                    if (d.info.indexOf('attackerLocated') >= 0) return '#d65222';
                    if (d.type == 'OR') return '#EEEE00';
                    if (d.type == 'AND') return '#66ccff';
                    return '#7CCD7C';
                };

                scope.link = scope.svg.append("g")
                    .attr("class", "links")
                    .selectAll("line")
                    .data(scope.links)
                    .enter()
                    .append("line")
                    .attr("x1", function(d) { return scope.nodes[d.source-1].x; })
                    .attr("y1", function(d) { return scope.nodes[d.source-1].y; })
                    .attr("x2", function(d) { return scope.nodes[d.target-1].x; })
                    .attr("y2", function(d) { return scope.nodes[d.target-1].y; });

                scope.node = scope.svg
                    .append("g")
                    .attr("class", "nodes")
                    .selectAll('g')
                    .data(scope.nodes)
                    .enter()
                    .append('g')
                    .attr('class', 'node')
                    .attr('transform', function(d) {
                        return 'translate('+d.x+' '+d.y+')';
                    })
                    .attr("fill", function(d) {
                        return scope.getColor(d);
                    })
                    .classed('important', function (d) {
                        return d.inPathTimes >= scope.paths.length;
                    }).on('click', function(d,i) {
                        var flag = !d3.select(this).classed('selected');
                        scope.node.classed('selected', false);
                        scope.link.classed('selected', false);
                        scope.selection = null;
                        if (flag) {
                            d3.select(this).classed('selected', true);
                            scope.link
                                .filter(function (edge) {
                                    return edge.source == d.id || edge.target == d.id;
                                }).classed('selected', true);
                            scope.selection = d;
                        }
                        scope.$apply();
                    });

                // add render
                scope.node
                    .append('use')
                    .classed('render', true)
                    .attr('xlink:href', function (d) {
                        return '#' + (d.type == 'LEAF' ? 'leaf' : 'nonleaf');
                    });
                // add body
                scope.node
                    .append('use')
                    .classed('body', true)
                    .attr('xlink:href', function (d) {
                        return '#' + (d.type == 'LEAF' ? 'leaf' : 'nonleaf');
                    });
                // add inner
                scope.node
                    .append('use')
                    .classed('inner', true)
                    .attr('xlink:href', function (d) {
                        return '#' + (d.type == 'LEAF' ? 'leaf' : 'nonleaf') + '_inner';
                    });

                // init position
                var gHeight = (maxLv * (scope.r + scope.gap) - scope.gap) * 2;
                var gWidth  = (maxSize * (scope.r * 2+scope.gap) - scope.gap);
                var ratio = height / (gHeight + 40);
                if (ratio > width / (gWidth + 40))
                    ratio = width / (gWidth + 40);
                if (ratio > 1) ratio = 1;
                scope.graphOffsetTop = (height - gHeight*ratio)/2;
                scope.graphOffsetLeft = (width - gWidth*ratio)/2;
                scope.graphRatio = ratio;
                scaleTo(0, 0, 1);
            };
                
            scope.drawPath = function (path) {
                scope.nodes.forEach(function (d) {
                    d.inPath = false;
                });
                scope.links.forEach(function (d) {
                    d.inPath = false;
                });
                path.forEach(function (d) {
                    scope.nodes[d-1].inPath = true;
                });
                scope.links.forEach(function (d) {
                    if (scope.nodes[d.source-1].inPath && scope.nodes[d.target-1].inPath) {
                        d.inPath = true;
                    }
                });
                scope.link.classed("light", true);
                scope.node.classed("light", true);
                scope.link
                    .filter(function (d) {return d.inPath;})
                    .classed("light", false);
                scope.node
                    .filter(function (d) {return d.inPath;})
                    .classed("light", false);
            }
            }    
        }
}]);
