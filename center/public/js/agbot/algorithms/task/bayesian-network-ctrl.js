/**
 * Created by Nettle on 2016/12/21.
 */
agbotApp
    .controller('bayesianNetworkCtrl', ['$http', '$scope', '$q', '$stateParams', '$state', function ($http, $scope, $q, $stateParams, $state) {
        $scope.task = {
            algorithm_id: null,
            result_id: null
        };

        $scope.resultCache = {};
        $scope.noResultFlag = true;
        $scope.nodeSelected = [];

        $q
            .all([$http.get(['api','algorithms'].join('/')), $http.get(['api','algorithms', $stateParams.algorithm_id].join('/'))])
            .then(function (result) {
                $scope.algorithm = result[1].data;
                $scope.algorithms = result[0].data.filter(function (algo) {
                    return algo.outputType === $scope.algorithm.inputType && algo.id != $scope.algorithm.id;
                });
            });

        $scope.selectAlgorithm = function () {
            $scope.task.result_id = null;
            if (!$scope.resultCache[$scope.task.algorithm_id]) {
                $http
                    .get(['api', 'algorithms', $scope.task.algorithm_id, 'tasks'].join('/'))
                    .success(function (data) {
                        $scope.resultCache[$scope.task.algorithm_id] = data;
                        $scope.results = $scope.resultCache[$scope.task.algorithm_id];
                        if ($scope.results.length < 1)
                            $scope.noResultFlag = true;
                        else {
                            $scope.noResultFlag = false;
                            $scope.task.result_id = $scope.results[0].id;
                        }
                    });
            }   else {
                $scope.results = $scope.resultCache[$scope.task.algorithm_id];
                if ($scope.results.length < 1)
                    $scope.noResultFlag = true;
                else {
                    $scope.noResultFlag = false;
                    $scope.task.result_id = $scope.results[0].id;
                }
            }
        };

        $scope.submit = function () {
            $scope.sending = true;
            console.log($scope.nodeSelected);
            var data = {
                algorithm_id: $scope.task.algorithm_id,
                result_id: $scope.task.result_id,
                attacked: $scope.nodeSelected.map(function (node) {
                    return {
                        id: node.id.replace(/\n/g, ''),
                        authority: node.authority
                    }
                })
            };
            console.log(data);

            $http
                .post(['api','algorithms', $stateParams.algorithm_id,'tasks'].join('/'), {
                    input: JSON.stringify(data)
                })
                .success(function (data) {
                    console.log(data);
                    $scope.sending = false;
                    window.location.href='/#/algorithms/3';
                })
                .error(function (data) {
                    $scope.sending = false;
                });
        };
    }])
    .directive('topologyEventGraph', ['$http', '$q', function ($http, $q) {
        return {
            restrict: 'E',
            replace: true,
            transclude: true,
            scope: {
                ready: '=ready',
                selected: '=nodeSelected'
            },
            template: '<svg id="topology_event_graph"></svg>',
            link: function (scope, element, attrs) {
                // get Data
                scope.nodes = [];
                scope.links = [];
                // get Node
                function getNode(id) {
                    for(var i = 0; i < scope.nodes.length; i++) {
                        if(scope.nodes[i].id.trim() == id.trim()) {
                            return scope.nodes[i];
                        }
                    }
                }

                $http.get(['api','sensors'].join('/')).success(function(sensors) {
                    sensors.forEach(function(sensor) {
                        sensor['name'] = sensor['name'].replace(/-/g, '_').replace(/\./g, '_');
                        var sensorNode = getNode(sensor['name']);
                        if(!sensorNode) {
                            sensorNode = {'id': sensor['name'], 'type': 'sensor', 'size': 20, 'ips': [sensor['ip']]};
                            scope.nodes.push(sensorNode);
                        }
                        sensor.hosts.forEach(function(host) {
                            host['nodeId'] = sensor['name'] + '-' + host['ip'];
                            host['nodeId'] = host['nodeId'].replace(/-/g, '_').replace(/\./g, '_');
                            scope.nodes.push({'id': host['nodeId'], 'type': 'host', 'size': 20, 'reports': host.reports});
                            scope.links.push({'source': sensor['name'], 'target': host['nodeId'], value: 1});
                        });
                    });
                    scope.drawGraph();
                    scope.ready = true;
                });

                var height = 400;
                var width = $('.container').width() * 10 / 12;
                // console.log(height);
                // console.log(width);
                // total
                scope.teg = d3.select('#topology_event_graph');
                scope.svg = scope.teg.append('g');
                // var height = scope.teg.getHeight();
                // var width  = scope.teg.getWidth();

                scope.teg.call(d3.zoom().scaleExtent([0.3, 3]).on("zoom", zoomed)).on("dblclick.zoom", null);

                function scaleTo(x, y, k) {
                    scope.svg.attr("transform",
                        'translate('+(x)+' '+(y)+')' + "scale(" + (k) + ")");
                }

                function zoomed() {
                    scaleTo(d3.event.transform.x,d3.event.transform.y,d3.event.transform.k);
                }
                scope.drawGraph = function () {
                    scope.color = d3.scaleOrdinal(d3.schemeCategory20);
                    // getType
                    scope.types = $.unique(scope.nodes.map(function (d) {
                        return d.type;
                    }));
                    for (var i = 0; i < scope.types.length; ++i) {
                        scope.nodes.forEach(function (node) {
                            if (node.type === scope.types[i]) node.typeId = i;
                        })
                    }

                    scope.nodes.forEach(function (node) {
                        node.selected = false;
                    });

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
                            if (d.type != 'host') return ;
                            if (!d.authority) d.authority = '1';
                            d.selected = !d.selected;
                            d3.select(this).classed('selected', d.selected);
                            scope.selected = scope.nodes.filter(function (node) {
                                return node.selected;
                            }).map(function (node) {
                                return node;
                            });
                            scope.$apply();
                        }).call(d3.drag()
                            .on("start", dragstarted)
                            .on("drag", dragged)
                            .on("end", dragended));

                    scope.node
                        .append('circle')
                        .attr('r', 15)
                        .attr('fill', function (d) {
                            return scope.color(d.typeId);
                        });

                    scope.node
                        .append('text')
                        .attr('y', 30)
                        .text(function (d) {
                            return d.id;
                        });

                    scope.node
                        .filter(function (d) {
                            return d.type == 'host'
                        })
                        .classed('isHost', true);

                    scope.simulation = d3.forceSimulation()
                        .force("link", d3.forceLink().id(function(d) { return d.id; }).distance(function(d) {return 80;}))
                        .force("charge", d3.forceManyBody().strength(function(d) {return -80;}))
                        .force("center", d3.forceCenter(width / 2, height / 2));

                    scope.simulation
                        .nodes(scope.nodes)
                        .on("tick", ticked);

                    scope.simulation
                        .force("link")
                        .links(scope.links);
                };

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