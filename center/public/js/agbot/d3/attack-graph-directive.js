/**
 * Created by Nettle on 2016/11/25.
 */

agbotApp.directive('attackGraph', ['$http', function ($http) {
    return {
        restrict: 'E',
        replace: true,
        transclude: true,
        scope: {
            id: '=rid',
            algorithm_id: '=algorithmid'
        },
        template: '<svg id="attack-graph"></svg>',
        link: function (scope, element, attrs) {

            scope.r = 30;
            scope.gap = 20;
            scope.svg = d3.select('#attack-graph');

            var height = scope.height ? scope.height : window.innerHeight;
            var width = scope.width ? scope.width : window.innerWidth;

            console.log(scope.algorithm_id + ' ' + scope.id);
            if (scope.id && scope.algorithm_id) {
                $http.get(['api','algorithms',scope.algorithm_id,'results',scope.id].join('/'))
                    .then(function (result) {
                        console.log(result.data);
                        scope.nodes = JSON.parse(result.data.content).nodes;
                        scope.links = JSON.parse(result.data.content).edges;

                        // setLevel
                        scope.start = 0;
                        scope.nodes.filter(function (a) {
                            if (a.info.indexOf('attackerLocated') >= 0) scope.start = a.id;
                        });
                        scope.nodes[0].lv = 1;
                        scope.nodes[0].offset = 0;

                        var seq = [], num = [0];
                        for (var i in scope.nodes) num.push(0);
                        num[1] = 1;
                        seq.push(1);

                        for (var i = 0; i < seq.length; ++i) {
                            var v = seq[i];
                            // scope.nodes[v - 1].child = [];
                            for (var j = 0; j < scope.links.length; ++j)
                                if (scope.links[j].target == v && !scope.nodes[scope.links[j].source - 1].lv) {
                                    // scope.nodes[v - 1].child.push(scope.links[j].source);
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

                        var defs = scope.svg.append("defs");

                        var arrows = [{id: 'arrow', color: 'black'}, {id: 'arrow_red', color: 'red'}];

                        var arrowMarker = defs.selectAll('marker')
                            .data(arrows)
                            .enter().append("marker")
                            .attr("id", function (d) {
                                return d.id;
                            })
                            .attr("markerUnits","strokeWidth")
                            .attr("markerWidth","12")
                            .attr("markerHeight","12")
                            .attr("viewBox","0 0 12 12")
                            .attr("refX","25")
                            .attr("refY","6")
                            .attr("orient","auto");

                        var arrow_path = "M2,2 L10,6 L2,10 L6,6 L2,2";

                        arrowMarker.append("path")
                            .attr("d",arrow_path)
                            .attr("fill",function (d) {
                                return d.color;
                            });

                        scope.getColor = function (d) {
                            if (d.id == 1) return '#FF3030';
                            if (d.id == scope.start) return '#d65222';
                            if (d.type == 'OR') return '#EEEE00';
                            if (d.type == 'AND') return '#66ccff';
                            return '#7CCD7C';
                        };

                        scope.link = scope.svg.append("g")
                            .attr("class", "links")
                            .attr("stroke","black")
                            .attr("stroke-width",2)
                            .selectAll("line")
                            .data(scope.links)
                            .enter()
                            .append("line")
                            .attr("x1", function(d) { return scope.nodes[d.source-1].x; })
                            .attr("y1", function(d) { return scope.nodes[d.source-1].y; })
                            .attr("x2", function(d) { return scope.nodes[d.target-1].x; })
                            .attr("y2", function(d) { return scope.nodes[d.target-1].y; })
                            .attr("marker-end","url(#arrow)");

                        scope.node_g = scope.svg.append("g")
                            .attr("class", "nodes")
                            .attr("stroke","black")
                            .attr("stroke-width",2);

                        scope.node = scope.node_g
                            .selectAll('g')
                            .data(scope.nodes)
                            .enter()
                            .append('g')
                            .attr('class', 'node')
                            .on('click', function(d,i) {
                                if (d3.select(this).attr("stroke") == "red") {
                                    d3.selectAll('.node').attr("stroke", "black");
                                    scope.link
                                        .attr("stroke","black")
                                        .attr("marker-end","url(#arrow)");;
                                }	else {
                                    d3.selectAll('.node').attr("stroke","black");
                                    scope.link
                                        .attr("stroke","black")
                                        .attr("marker-end","url(#arrow)");;
                                    d3.select(this).attr("stroke", "red");
                                    scope.link
                                        .filter(function (edge) {
                                            return edge.source == d.id || edge.target == d.id;
                                        })
                                        .attr("stroke","red")
                                        .attr("marker-end","url(#arrow_red)");;
                                }
                            });

                        scope.node.filter(function (d) {
                            return !(d.type == 'LEAF');
                        }).attr('transform', function(d) {
                            return 'translate('+d.x+' '+d.y+')';
                        }).append('circle')
                            .attr("r", function(d) { return scope.r; })
                            .attr("fill", function(d) { return scope.getColor(d); })
                            .attr("stroke-width",2);

                        scope.node.filter(function (d) {
                            return d.type == 'LEAF';
                        }).attr('transform', function(d) {
                            return 'translate('+(d.x-scope.r)+' '+(d.y-scope.r)+')';
                        }).append('rect')
                            .attr("width", function(d) { return scope.r*2; })
                            .attr("height", function(d) { return scope.r*2; })
                            .attr("fill", function(d) { return scope.getColor(d); });
                    });
            }
        }
    }
}]);
