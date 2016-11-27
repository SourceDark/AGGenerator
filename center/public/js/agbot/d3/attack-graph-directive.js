/**
 * Created by Nettle on 2016/11/25.
 */

agbotApp.directive('attackGraph', ['$http', '$q', function ($http, $q) {
    return {
        restrict: 'E',
        replace: true,
        transclude: true,
        scope: {
            id: '=rid',
            algorithm_id: '=algorithmid'
        },
        template: '<div>\
            <div class="infobar">\
                <h3 ng-if="analysis">Attack Path</h3>\
                <div ng-if="analysis" class="infobar-content">\
                    <div class="path_id" ng-click="showPath(-1)" ng-class="{true: \'active\'}[selectedPathId==-1]">All Paths</div>\
                    <div class="path_id" ng-repeat="path in analysis.PathList" ng-click="showPath($index)" ng-class="{true: \'active\'}[$index==selectedPathId]">Path #{{$index+1}}<span>{{path.length}} nodes</span></div>\
                </div>\
                <h3>Selection Detail</h3>\
                <div ng-if="selection" class="infobar-content">\
                    <p>ID: <span ng-bind="selection.id"></span></p>\
                    <p>Type: <span ng-bind="selection.type"></span></p>\
                    <p>Info: <span ng-bind="selection.info"></span></p>\
                </div>\
                <div ng-if="!selection" class="infobar-content">\
                    <p>Select an element in the visualization.</p>\
                </div>\
            </div>\
            <svg id="attack-graph"></svg>\
        </div>',
        link: function (scope, element, attrs) {
            var height = window.innerHeight - 51;
            var width  = window.innerWidth -  256;
            // console.log(height + ' ' + width);
            scope.r = 30;
            scope.dr = 4;
            scope.gap = 20;
            scope.selectedPathId = -1;
            scope.ag = d3.select('#attack-graph');
            scope.svg = scope.ag.append('g');

            // console.log(scope.algorithm_id + ' ' + scope.id);
            if (scope.id && scope.algorithm_id) {
                var request = [
                    $http.get(['api','algorithms',scope.algorithm_id].join('/')),
                    $http.get(['api','algorithms',scope.algorithm_id,'results',scope.id].join('/'))
                ];
                $q.all(request).then( function (result) {
                    // console.log(result[1].data.content);
                    if (result[0].data.type == 1) {
                        scope.analysis = JSON.parse(result[1].data.content);
                        // console.log(scope.analysis);
                        $http.get(['api','algorithms',scope.analysis.input.algorithm_id,'results',scope.analysis.input.result_id].join('/'))
                            .then(function (result) {
                                scope.nodes = JSON.parse(result.data.content).nodes;
                                scope.links = JSON.parse(result.data.content).edges;
                                scope.drawGraph();
                            });
                    }   else {
                        scope.nodes = JSON.parse(result[1].data.content).nodes;
                        scope.links = JSON.parse(result[1].data.content).edges;
                        scope.drawGraph();
                    }
                });

                scope.showPath = function (id) {
                    scope.selectedPathId = id;
                    if (id != -1) scope.drawPath(scope.analysis.PathList[id]);
                    else {
                        scope.link.classed("light", false);
                        scope.node.classed("light", false);
                    }
                };

                scope.drawGraph = function () {
                    scope.ag.call(d3.zoom().scaleExtent([0.3, 3]).on("zoom", zoomed)).on("dblclick.zoom", null);

                    function scaleTo(x, y, k) {
                        scope.svg.attr("transform",
                            'translate('+(x+scope.graphOffsetLeft*k)+' '+(y+scope.graphOffsetTop*k)+')' + "scale(" + (k*scope.graphRatio) + ")");
                    }

                    function zoomed() {
                        scaleTo(d3.event.transform.x,d3.event.transform.y,d3.event.transform.k);
                    }

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
                        .selectAll("line")
                        .data(scope.links)
                        .enter()
                        .append("line")
                        .attr("x1", function(d) { return scope.nodes[d.source-1].x; })
                        .attr("y1", function(d) { return scope.nodes[d.source-1].y; })
                        .attr("x2", function(d) { return scope.nodes[d.target-1].x; })
                        .attr("y2", function(d) { return scope.nodes[d.target-1].y; });

                    scope.node_g = scope.svg.append("g")
                        .attr("class", "nodes")

                    scope.node = scope.node_g
                        .selectAll('g')
                        .data(scope.nodes)
                        .enter()
                        .append('g')
                        .attr('class', 'node')
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

                    // render
                    scope.node.filter(function (d) {
                        return !(d.type == 'LEAF');
                    }).attr('transform', function(d) {
                        return 'translate('+d.x+' '+d.y+')';
                    }).append('circle')
                        .attr("class", 'render')
                        .attr("r", function(d) { return scope.r; })
                        .attr("fill", function(d) { return scope.getColor(d); });

                    scope.node.filter(function (d) {
                        return d.type == 'LEAF';
                    }).attr('transform', function(d) {
                        return 'translate('+(d.x-scope.r)+' '+(d.y-scope.r)+')';
                    }).append('rect')
                        .attr("class", 'render')
                        .attr("width", function(d) { return scope.r*2; })
                        .attr("height", function(d) { return scope.r*2; })
                        .attr("fill", function(d) { return scope.getColor(d); });

                    scope.node.filter(function (d) {
                        return !(d.type == 'LEAF');
                    }).attr('transform', function(d) {
                        return 'translate('+d.x+' '+d.y+')';
                    }).append('circle')
                        .attr("r", function(d) { return scope.r; })
                        .attr("fill", function(d) { return scope.getColor(d); });

                    scope.node.filter(function (d) {
                        return d.type == 'LEAF';
                    }).attr('transform', function(d) {
                        return 'translate('+(d.x-scope.r)+' '+(d.y-scope.r)+')';
                    }).append('rect')
                        .attr("width", function(d) { return scope.r*2; })
                        .attr("height", function(d) { return scope.r*2; })
                        .attr("fill", function(d) { return scope.getColor(d); });

                    // important
                    scope.analysis.PathList.forEach(function (path) {
                        path.forEach(function (d) {
                            if (!scope.nodes[d - 1].inPathTimes)
                                scope.nodes[d - 1].inPathTimes = 0;
                            scope.nodes[d - 1].inPathTimes ++;
                        })
                    });
                    scope.node.filter(function (d) {
                        return !(d.type == 'LEAF') && d.inPathTimes == scope.analysis.PathList.length;
                    }).attr('transform', function(d) {
                        return 'translate('+d.x+' '+d.y+')';
                    }).append('circle')
                        .attr("r", function(d) { return scope.r - scope.dr; })
                        .attr("fill", function(d) { return scope.getColor(d); });

                    scope.node.filter(function (d) {
                        return d.type == 'LEAF' && d.inPathTimes == scope.analysis.PathList.length;
                    }).attr('transform', function(d) {
                        return 'translate('+(d.x-scope.r+scope.dr)+' '+(d.y-scope.r+scope.dr)+')';
                    }).append('rect')
                        .attr("x", scope.dr)
                        .attr("y", scope.dr)
                        .attr("width", function(d) { return scope.r*2 - scope.dr*2; })
                        .attr("height", function(d) { return scope.r*2 - scope.dr*2; })
                        .attr("fill", function(d) { return scope.getColor(d); });

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
                    // console.log(scope.links);
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
    }
}]);
