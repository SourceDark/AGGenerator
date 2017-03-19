/**
 * Created by Nettle on 2017/3/14.
 */

(function () {
    'use strict';

    angular.module('BlurAdmin.pages.asset.manage', [])
        .config(routeConfig)
        .controller('assetManageCtrl', function ($scope, $stateParams, $http) {
            $scope.data = topoData;
            $scope.$watch('host', function () {
            })
        })
        .directive('topologyGraph', ['$http', '$q', function ($http, $q) {
            return {
                restrict: 'E',
                replace: true,
                transclude: true,
                scope: {
                    data: '=graphData',
                    options: '=options'
                },
                templateUrl: 'app/pages/asset/manage/topologyGraph.html',
                link: function (scope, element, attrs) {

                    scope.r = 15;
                    scope.x_gap = 50;
                    scope.y_gap = 40;

                    // init tree
                    scope.idPool = {};
                    scope.ipPool = {};
                    for (var i in scope.data) {
                        scope.data[i].id = parseInt(i) + 1;
                        scope.idPool[ scope.data[i].id ] = scope.data[i];
                        scope.ipPool[ scope.data[i].inner_interface] = scope.data[i];
                        if (scope.data[i].outer_interface)
                            scope.ipPool[ scope.data[i].outer_interface] = scope.data[i];
                        scope.data[i].child = [];
                    }

                    scope.links = [];
                    scope.data.forEach(function (host) {
                       host.father = 0;
                       // console.log(host);
                       if (host.gateway && scope.ipPool[host.gateway]) {
                           host.father = scope.ipPool[host.gateway].id;
                           scope.idPool[host.father].child.push(host.id);
                           scope.links.push({
                               target: scope.idPool[host.id],
                               source: scope.idPool[host.father]
                           });
                       }
                       if (host.father == 0)
                           scope.root = scope.idPool[host.id];
                    });
                    // init over

                    // set postion

                    scope.root.level = 1;
                    deal(scope.root, 0);

                    function deal(host, offset) {
                        host.y = host.level * scope.x_gap;
                        host.height = 0;
                        host.child.forEach(function (chdId) {
                            var chdHost = scope.idPool[chdId];
                            chdHost.level = host.level + 1;
                            deal(chdHost, host.height + offset);
                            host.height += chdHost.height + scope.y_gap;
                        });
                        if (host.child.length) host.height -= scope.y_gap;
                        host.x = host.height / 2 + offset;
                        host.y += scope.r;
                        host.x += scope.r;
                    }
                    // set over

                    var height = $('#topology_graph').height();
                    var width  = $('#topology_graph').width();

                    scope.tg = d3.select('#topology_graph');
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
                        .data(scope.links)
                        .enter()
                        .append("line")
                        .attr("x1", function(d) { return d.source.x; })
                        .attr("y1", function(d) { return d.source.y; })
                        .attr("x2", function(d) { return d.target.x; })
                        .attr("y2", function(d) { return d.target.y; });

                    scope.node = scope.svg
                        .append("g")
                        .attr("class", "nodes")
                        .selectAll('g')
                        .data(scope.data)
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
                        });

                    scope.node
                        .append('use')
                        .attr('xlink:href', function (d) {
                            return '#' + (d.outer_interface ? 'router' : 'host');
                        });

                    var gHeight = 0;
                    var gWidth  = 0;
                    scope.data.forEach(function (host) {
                        if (host.y > gHeight) gHeight = host.y;
                        if (host.x > gWidth) gWidth = host.x;
                    });

                    gHeight += scope.r;
                    gWidth += scope.r;

                    width -= 200;
                    var ratio = height / (gHeight + 10);
                    if (ratio > width / (gWidth + 10))
                        ratio = width / (gWidth + 10);
                    if (ratio > 1) ratio = 1;
                    scope.graphOffsetTop = (height - gHeight*ratio)/2;
                    scope.graphOffsetLeft = (width - gWidth*ratio)/2;
                    scope.graphRatio = ratio;
                    scaleTo(0, 0, 1);
                }
            };
        }]);

    /** @ngInject */
    function routeConfig($stateProvider) {
        $stateProvider
            .state('asset.manage', {
                url: '/manage',
                templateUrl: 'app/pages/asset/manage/manage.html',
                title: '资产管理'
            });
    }

})();