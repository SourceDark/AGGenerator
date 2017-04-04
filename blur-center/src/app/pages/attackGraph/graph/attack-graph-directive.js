/**
 * Created by Nettle on 2017/4/4.
 */

angular.module('BlurAdmin.pages.attackGraph.graph')
    .directive('attackGraph', ['$http', '$q', function ($http, $q) {
        return {
            restrict: 'E',
            replace: true,
            transclude: true,
            scope: {
                data: '=graphData',
                paths: '=pathData'
            },
            templateUrl: 'app/pages/attackGraph/graph/attack-graph-v1.html',
            link: function (scope, element, attrs) {

                scope.gap = 120;
                scope.r = 32;

                scope.idPool = {};
                scope.data.nodes.forEach(function (node) {
                    scope.idPool[ node.id ] = node;
                });
                scope.data.edges.forEach(function (edge) {
                    edge.source = scope.idPool[ edge.source ];
                    edge.target = scope.idPool[ edge.target ];
                });

                //
                scope.idx = [];
                scope.root = scope.idPool['1'];
                scope.root.level = 1;

                var seq = [];
                seq.push(scope.root);
                // bfs setLevel
                for (var i = 0; i < seq.length; ++i) {
                    var u = seq[i];
                    scope.data.edges.forEach(function (edge) {
                        if (edge.target.id == u.id && !edge.source.level) {
                            edge.source.level = u.level + 1;
                            seq.push(edge.source);
                        }
                    });
                }

                scope.maxLevel = Math.max.apply(null,
                    scope.data.nodes.map(function (node) {
                        return node.level;
                    })
                );
                console.log(scope.maxLevel);

                var idx = [];
                for (i = 0; i <= scope.maxLevel; ++i)
                    idx.push(0);
                scope.data.nodes.forEach(function (node) {
                    node.idx = idx[ node.level ]++;
                });
                scope.maxWidth = (Math.max.apply(null, idx) - 1) * scope.gap;
                console.log(scope.maxWidth);

                console.log(scope.data.nodes);

                // set position
                scope.data.nodes.forEach(function (node) {
                    var offset = (scope.maxWidth - (idx[ node.level ] - 1) * scope.gap) / 2;
                    node.y = (scope.maxLevel - node.level) * scope.gap;
                    node.x = offset + node.idx * scope.gap;
                });

                // set over

                // draw
                var height = $('#attack_graph').height();
                var width  = $('#attack_graph').width();

                scope.tg = d3.select('#attack_graph');
                scope.svg = scope.tg.append('g');

                scope.tg.call(d3.zoom().scaleExtent([0.3, 4]).on("zoom", zoomed)).on("dblclick.zoom", null);

                function scaleTo(x, y, k) {
                    scope.svg.attr("transform",
                        'translate('+(x+scope.graphOffsetLeft*k)+' '+(y+scope.graphOffsetTop*k)+')' + "scale(" + (k*scope.graphRatio) + ")");
                }

                function zoomed() {
                    scaleTo(d3.event.transform.x,d3.event.transform.y,d3.event.transform.k);
                }

                scope.link = scope.svg.append("g")
                    .attr("class", "links")
                    .selectAll("line")
                    .data(scope.data.edges)
                    .enter()
                    .append("line")
                    .attr('class', 'link')
                    .attr("x1", function(d) { return d.source.x; })
                    .attr("y1", function(d) { return d.source.y; })
                    .attr("x2", function(d) { return d.target.x; })
                    .attr("y2", function(d) { return d.target.y; });

                scope.node = scope.svg
                    .append("g")
                    .attr("class", "nodes")
                    .selectAll('g')
                    .data(scope.data.nodes)
                    .enter()
                    .append('g')
                    .attr('class', 'node')
                    .attr('transform', function(d) {
                        return 'translate('+d.x+' '+d.y+')';
                    })
                    .on('click', function(d,i) {
                        var flag = !d3.select(this).classed('selected');
                        scope.node.classed('selected', false);
                        scope.selection = null;
                        if (flag) {
                            d3.select(this).classed('selected', true);
                            scope.selection = d;
                        }
                        scope.$apply();
                    })
                    .attr('fill', function (d) {
                        if (d.id == '1') return 'red';
                        if (d.attacker) return 'purple';
                        if (d.type == 'LEAF') return 'green';
                        if (d.type == 'OR') return 'orange';
                        return 'blue';
                    });

                scope.node
                    .append('use')
                    .attr('xlink:href', function (d) {
                        return '#node';
                    })
                    .attr('class', 'ori');

                scope.node
                    .append('use')
                    .attr('xlink:href', function (d) {
                        return '#node_selected';
                    })
                    .attr('class', 'active');

                scope.node
                    .append('text')
                    .text(function (d) {
                        return d.info;
                    })
                    .attr('y', 45);

                var gHeight = (scope.maxLevel - 1) * scope.gap + scope.r * 2;
                var gWidth  = scope.maxWidth + scope.r * 2;

                console.log(gHeight + ' ' + gWidth);

                width -= 200;
                var ratio = height / (gHeight + 10);
                if (ratio > width / (gWidth + 10))
                    ratio = width / (gWidth + 10);
                if (ratio > 1) ratio = 1;
                scope.graphOffsetTop = scope.r*ratio + (height - gHeight*ratio) / 2;
                scope.graphOffsetLeft =  (width - gWidth*ratio) / 2;
                scope.graphRatio = ratio;
                scaleTo(0, 0, 1);

                // function

                scope.showPath = function (id) {
                    scope.link.classed('selected', false);
                    if (id != -1) {
                        scope.data.nodes.forEach(function (node) {
                            node.inPath = false;
                        });
                        scope.paths[id].forEach(function (id) {
                            scope.idPool[id].inPath = true;
                        });
                        scope.link.filter(function (d) {
                            return d.source.inPath && d.target.inPath && (d.source.level > d.target.level)
                        }).classed('selected', true);
                    }
                }
            }
        }
    }]);