/**
 * Created by Nettle on 2016/12/28.
 */

agbotApp.directive('attackGraphIi', ['attackGraphService', function (attackGraphService) {
    return {
        restrict: 'E',
        replace: true,
        transclude: true,
        scope: {
            originalNodes: '=nodes',
            originalLinks: '=links',
            infomation: '=infomation'
        },
        templateUrl: 'html/algorithms/visualization/attack_graph_2',
        link: function (scope, element, attrs) {

            var height = window.innerHeight - 51;
            var width  = window.innerWidth -  256;

            scope.ag = d3.select('#attack-graph');
            scope.svg = scope.ag.append('g');

            scope.ag.call(d3.zoom().on("zoom", zoomed)).on("dblclick.zoom", null);

            function scaleTo(x, y, k) {
                scope.svg.attr('transform', 'translate('+
                    (x + scope.graphOffsetLeft * k) + ' '+
                    (y + scope.graphOffsetTop*k) + ')' + "scale(" +
                    (k * scope.graphRatio) + ")");
            }

            function zoomed() {
                scaleTo(d3.event.transform.x, d3.event.transform.y, d3.event.transform.k);
            }

            scope.$watchGroup(['originalNodes','originalLinks','infomation'], function() {
                if (scope.originalNodes && scope.originalLinks) {
                    // var newGraph = attackGraphService.simplify(
                    //     angular.copy(scope.originalNodes),
                    //     angular.copy(scope.originalLinks)
                    // );
                    // scope.nodes = newGraph.nodes;
                    // scope.links = newGraph.links;

                    scope.nodes = scope.originalNodes;
                    scope.links = scope.originalLinks;
                    scope.drawGraph();
                }
            });
            
            scope.drawGraph = function () {
                scope.svg.html('');

                // calculateCoordinates
                scope.size = attackGraphService.calculateCoordinates(
                    scope.nodes,
                    scope.links,
                    scope.nodes
                        .filter(function (node) {
                            return node.goal;
                        })
                        .map(function (node) {
                            return node.id;
                        })
                    ,scope.nodes
                        .filter(function (node) {
                            return node.nodeType == 'attacker'
                        })
                        .map(function (node) {
                            return node.id;
                        })
                );

                // draw
                scope.link = attackGraphService.drawLinks(scope.links, scope.svg);
                scope.node = attackGraphService.drawNodes(scope.nodes, scope.svg);

                // init position
                var ratio = height / (scope.size.height + 40);
                if (ratio > width / (scope.size.width + 40))
                    ratio = width / (scope.size.width + 40);
                if (ratio > 1) ratio = 1;
                scope.graphOffsetTop = (height - scope.size.height * ratio) / 2;
                scope.graphOffsetLeft = (width - scope.size.width * ratio) / 2;
                scope.graphRatio = ratio;
                scaleTo(0, 0, 1);

                // setEvent
                scope.node
                    .on('click', function(d,i) {
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

            };
        }
    }
}]);
