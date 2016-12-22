/**
 * Created by Nettle on 2016/11/25.
 */

agbotApp.directive('attackGraph', [function () {
    return {
        restrict: 'E',
        replace: true,
        transclude: true,
        scope: {
            originalNodes: '=nodes',
            originalLinks: '=links',
            infomation: '=infomation'
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

            scope.$watchGroup(['originalNodes','originalLinks','infomation'], function() {
                if (scope.infomation && scope.infomation.PathList)
                    scope.paths = scope.infomation.PathList;
                if (scope.infomation && scope.infomation.probabilities)
                    scope.probabilities = scope.infomation.probabilities;
                if (scope.originalNodes && scope.originalLinks) {
                    scope.nodes = angular.copy(scope.originalNodes);
                    scope.links = angular.copy(scope.originalLinks);
                    scope.simplify();
                    scope.drawGraph();
                }
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
                scope.svg.html('');

                // calculate coordinate
                scope.nodes.forEach(function (d) {
                    d.lv = 0;
                });
                scope.nodes[0].lv = 1;
                scope.nodes[0].offset = 0;

                var seq = [], num = [0];
                for (var i in scope.nodes) num.push(0);
                num[1] = 1;
                seq.push(1);

                for (var i = 0; i < seq.length; ++i) {
                    var v = seq[i];
                    for (var j = 0; j < scope.links.length; ++j)
                        //if (scope.links[j].target == v && !scope.nodes[scope.links[j].source - 1].lv && scope.nodes[scope.links[j].source - 1].type != 'LEAF') {
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

                if (scope.probabilities) {
                    for (var i = 1; i <= scope.nodes.length; ++i) {
                        scope.nodes[i - 1].probability = scope.probabilities[i - 1];
                    }
                }

                scope.getColor = function (d) {
                    if (d.id == 1) return '#FF3030';
                    if (d.info.indexOf('attackerLocated') >= 0 || d.type == 'START') return '#d65222';
                    if (d.type == 'OR') return '#EEEE00';
                    if (d.type == 'AND') return '#66ccff';
                    return '#7CCD7C';
                };

                scope.link = scope.svg.append("g")
                    .attr("class", "links")
                    .selectAll("line")
                    .data(scope.links)
                    .enter()
                    // .filter(function (d) {
                    //     return scope.nodes[d.source-1].type != 'LEAF' && scope.nodes[d.target-1].type != 'LEAF';
                    // })
                    .append("line")
                    // .attr('d', function (d) {
                    //     return "M" +  scope.nodes[d.source-1].x + ' ' + scope.nodes[d.source-1].y + 'L' + scope.nodes[d.target-1].x + ' ' + scope.nodes[d.target-1].y;
                    //     //x*cosA-y*sinA  x*sinA+y*cosA
                    //     var _x = scope.nodes[d.target-1].x - scope.nodes[d.source-1].x;
                    //     var _y = scope.nodes[d.target-1].y - scope.nodes[d.source-1].y;
                    //     var newX = (_x * Math.cos(0.785) - _y * Math.sin(0.785)) / Math.sqrt(2);
                    //     var newY = (_x * Math.sin(0.785) + _y * Math.cos(0.785)) / Math.sqrt(2);
                    //     return "M" +  scope.nodes[d.source-1].x + ' ' + scope.nodes[d.source-1].y + 'S' + (scope.nodes[d.source-1].x + newX) + ' ' + (scope.nodes[d.source-1].y + newY) + ' ' + scope.nodes[d.target-1].x + ' ' + scope.nodes[d.target-1].y;
                    // })
                    // .attr('fill', 'none');
                    .attr("x1", function(d) { return scope.nodes[d.source-1].x; })
                    .attr("y1", function(d) { return scope.nodes[d.source-1].y; })
                    .attr("x2", function(d) { return scope.nodes[d.target-1].x; })
                    .attr("y2", function(d) { return scope.nodes[d.target-1].y; });

                //<path d="M10 10L90 90" stroke="#000000" style="stroke-width: 5px;"></path>

                scope.node = scope.svg
                    .append("g")
                    .attr("class", "nodes")
                    .selectAll('g')
                    .data(scope.nodes)
                    .enter()
                    // .filter(function (d) {
                    //     return d.type != 'LEAF'
                    // })
                    .append('g')
                    .attr('class', 'node')
                    .attr('transform', function(d) {
                        return 'translate('+d.x+' '+d.y+')';
                    })
                    .attr("fill", function(d) {
                        return scope.getColor(d);
                    })
                    .classed('important', function (d) {
                        return scope.paths && d.inPathTimes >= scope.paths.length;
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
            };

            scope.simplify = function () {
                var newLinks = [];
                scope.orNodeCount = 0;
                scope.nodes.forEach(function (d) {
                    if (d.type == 'OR' || d.info.indexOf('attackerLocated') >= 0) {
                        d.type = 'OR';
                        d.mark = d.id;
                        d.orNodeId = ++scope.orNodeCount;
                    }
                });
                scope.links.forEach(function (d) {
                    if (scope.nodes[d.source - 1].type == 'AND')
                        scope.nodes[d.source - 1].mark = d.target;
                });
                scope.links.forEach(function (d) {
                    if (scope.nodes[d.target - 1].type == 'AND') {
                        if (scope.nodes[d.source - 1].type == 'LEAF')
                            scope.nodes[d.source - 1].mark = scope.nodes[d.target - 1].mark;
                        else
                            newLinks.push({
                                source: d.source,
                                target: scope.nodes[d.target - 1].mark
                            });
                    }
                });
                
                scope.newId = scope.nodes.map(function (d) {
                    return scope.nodes[d.mark - 1].orNodeId;
                });

                var newNodes = [];
                scope.nodes.forEach(function (d) {
                    if (d.type == 'OR') {
                        newNodes.push({
                            id: d.orNodeId,
                            originalId: d.id,
                            info: d.info
                        });
                    }
                });
                newLinks.forEach(function (d) {
                    d.source = scope.newId[d.source - 1];
                    d.target = scope.newId[d.target - 1];
                });

                // path
                if (scope.paths) {
                    scope.paths = scope.paths.map(function (path) {
                       return $.unique(path.map(function (d) {
                           return scope.newId[d - 1];
                       }));
                    });
                }
                // pro
                if (scope.probabilities) {
                    scope.probabilities = newNodes.map(function (d) {
                        return scope.probabilities[d.originalId - 1];
                    });
                }

                newNodes.forEach(function (d) {
                    if (d.info.indexOf('attackerLocated') >= 0)
                        d.type = 'LEAF';
                    else d.type = 'OR';
                });

                scope.nodes = newNodes;
                scope.links = newLinks;
            };
        }
    }
}]);
