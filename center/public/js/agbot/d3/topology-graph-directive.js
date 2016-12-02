/**
 * Created by Nettle on 2016/11/27.
 */

agbotApp.directive('topologyGraph', ['$http', '$q', function ($http, $q) {
    return {
        restrict: 'E',
        replace: true,
        transclude: true,
        scope: {
            nodes: '=nodes',
            links: '=links',
            options: '=options'
        },
        templateUrl: 'html/vision/topology_template',
        link: function (scope, element, attrs) {
            var height = window.innerHeight - 51;
            var width  = window.innerWidth -  256;

            scope.tg = d3.select('#topology-graph');
            scope.svg = scope.tg.append('g');

            scope.tg.call(d3.zoom().scaleExtent([0.3, 3]).on("zoom", zoomed)).on("dblclick.zoom", null);

            function scaleTo(x, y, k) {
                scope.svg.attr("transform",
                    'translate('+(x)+' '+(y)+')' + "scale(" + (k) + ")");
            }

            function zoomed() {
                scaleTo(d3.event.transform.x,d3.event.transform.y,d3.event.transform.k);
            }

            scope.link = scope.svg
                .append("g")
                .attr("class", "links")
                .selectAll("line")
                .data(scope.links)
                .enter()
                .append("line");

            scope.node = scope.svg
                .append("g")
                .attr("class", "nodes")
                .selectAll(".node")
                .data(scope.nodes)
                .enter()
                .append("g")
                .attr('class', 'node')
                .on('click', function (d) {
                    var flag = !d3.select(this).classed('selected');
                    scope.node.classed('selected', false);
                    scope.selection = null;
                    if (flag) {
                        d3.select(this).classed('selected', true);
                        scope.selection = d;
                        if(scope.selection.reports && !scope.selection.cves) {
                            var cveIds = [], filter = "";
                            scope.selection.reports.forEach(function(resport) {
                                if(!resport.cves == '[]') {
                                    return;
                                }
                                resport.cves.substr(1, resport.cves.length - 2).split(",").forEach(function(cveId) {
                                    cveId = cveId.substr(1, cveId.length - 2);
                                    if(cveIds.indexOf(cveId) >= 0) {
                                        return;
                                    }
                                    cveIds.push(cveId);
                                    filter += cveId + ",";
                                });
                            });
                            scope.selection.cveIds = cveIds;
                            if(cveIds.length) {
                                $http.get("/api/cve?filter=" + filter).success(function(cves){
                                    scope.selection.cves = cves;
                                });    
                            } else {
                                scope.selection.cves = [];
                            }
                            
                        }
                    }
                    scope.$apply();
                }).call(d3.drag()
                    .on("start", dragstarted)
                    .on("drag", dragged)
                    .on("end", dragended));

            scope.node
                .append('use')
                .attr('xlink:href', function (d) {
                    return '#' + d.type;
                });

            scope.node
                .append('text')
                .attr('y', 50)
                .text(function (d) {
                    return d.id;
                });

            scope.simulation = d3.forceSimulation()
                .force("link", d3.forceLink().id(function(d) { return d.id; }).distance(function(d) {return scope.options.distance;}))
                .force("charge", d3.forceManyBody().strength(function(d) {return scope.options.charge;}))
                .force("center", d3.forceCenter(width / 2, height / 2));

            scope.simulation
                .nodes(scope.nodes)
                .on("tick", ticked);

            scope.simulation
                .force("link")
                .links(scope.links);

            function ticked() {
                scope.link
                    .attr("x1", function(d) { return d.source.x; })
                    .attr("y1", function(d) { return d.source.y; })
                    .attr("x2", function(d) { return d.target.x; })
                    .attr("y2", function(d) { return d.target.y; });

                scope.node
                    .attr('transform', function (d) {
                        return 'translate('+ d.x + ' ' + d.y + ')';
                    });
            }

            function dragstarted(d) {
                if (!d3.event.active) scope.simulation.alphaTarget(0.3).restart();
                d.fx = d.x;
                d.fy = d.y;
            }

            function dragged(d) {
                d.fx = d3.event.x;
                d.fy = d3.event.y;
            }

            function dragended(d) {
                if (!d3.event.active) scope.simulation.alphaTarget(0);
                d.fx = null;
                d.fy = null;
            }
        }
    }
}]);