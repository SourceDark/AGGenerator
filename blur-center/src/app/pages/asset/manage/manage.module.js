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
                    scope.root.level = 0;
                    scope.maxLevel = 0;
                    setLevel(scope.root, 0);

                    function setLevel(host) {
                        if (scope.maxLevel < host.level)
                            scope.maxLevel = host.level;
                        host.child.forEach(function (chdId) {
                            var chdHost = scope.idPool[chdId];
                            chdHost.level = host.level + 1;
                            setLevel(chdHost);
                        });
                    }

                    // Math.atan(x)
                    function check(host, r) {
                        var self_half_radian = host.level ? Math.atan(16 / (r * host.level)) : 0;
                        var chd_half_radian = Math.atan(16 / (r * (host.level + 1)));
                        var chd_radian = 0;
                        // if (host.level < 1)
                        host.child.forEach(function (chdId) {
                            var chdHost = scope.idPool[chdId];
                            chdHost.level = host.level + 1;
                            chd_radian += check(chdHost, r);
                        });
                        if (host.child.length) chd_radian += chd_half_radian * (host.child.length - 1);
                        return Math.max(self_half_radian * 2, chd_radian);
                    }

                    var left = 50, right = 50000, tpRadian;
                    while (left + 1 <= right) {
                        var mid = (left + right) / 2;
                        tpRadian = check(scope.root, mid);
                        console.log(tpRadian + ' ' + mid);
                        if (tpRadian <= Math.PI * 2) right = mid;
                        else left = mid;
                    }

                    function setPosition(host, r, offset) {
                        var self_half_radian = host.level ? Math.atan(16 / (r * host.level)) : 0;
                        var chd_half_radian = Math.atan(16 / (r * (host.level + 1)));
                        var chd_radian = 0;
                        host.child.forEach(function (chdId) {
                            var chdHost = scope.idPool[chdId];
                            chdHost.level = host.level + 1;
                            chd_radian += setPosition(chdHost, r, chd_radian + offset) + chd_half_radian;
                        });
                        if (host.child.length) chd_radian -= chd_half_radian;
                        var this_radian = Math.max(self_half_radian, chd_radian / 2);
                        host.x = Math.sin(this_radian + offset) * host.level * r;
                        host.y = Math.cos(this_radian + offset) * host.level * r;
                        console.log(host.inner_interface + ' ' + host.x + ' ' + host.y + ' ' + this_radian + ' ' + offset);
                        return Math.max(self_half_radian * 2, chd_radian);
                    }

                    console.log(left);
                    setPosition(scope.root, left, 0);
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
                        .attr('class', 'link')
                        .attr("x1", function(d) { return d.source.x; })
                        .attr("y1", function(d) { return d.source.y; })
                        .attr("x2", function(d) { return d.target.x; })
                        .attr("y2", function(d) { return d.target.y; })
                        .on('click', function(d,i) {
                            var flag = !d3.select(this).classed('selected');
                            scope.node.classed('selected', false);
                            scope.link.classed('selected', false);
                            scope.selection = null;
                            if (flag) {
                                d3.select(this).classed('selected', true);
                                scope.selection = d;
                            }
                            scope.$apply();
                        });

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
                            scope.link.classed('selected', false);
                            scope.selection = null;
                            if (flag) {
                                d3.select(this).classed('selected', true);
                                scope.selection = d;
                            }
                            scope.$apply();
                        })
                        .attr('fill', function (d) {
                            return (d.outer_interface ? 'green' : 'blue');
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
                        .attr('xlink:href', function (d) {
                            return '#node_selected';
                        })
                        .attr('class', 'active');


                    var max_x = -99999, max_y = -99999, min_x = 99999, min_y = 9999;
                    scope.data.forEach(function (host) {
                        if (max_x < host.x) max_x = host.x;
                        if (max_y < host.y) max_y = host.y;
                        if (min_x > host.x) min_x = host.x;
                        if (min_y > host.y) min_y = host.y;
                    });

                    var gHeight = max_x - min_x + 64;
                    var gWidth  = max_y - min_y + 64;

                    console.log(gHeight + ' ' + gWidth);

                    width -= 200;
                    var ratio = height / (gHeight + 10);
                    if (ratio > width / (gWidth + 10))
                        ratio = width / (gWidth + 10);
                    if (ratio > 1) ratio = 1;
                    scope.graphOffsetTop = -(min_y-16)*ratio + (height - gHeight*ratio) / 2;
                    scope.graphOffsetLeft = -(min_x-16)*ratio + (width - gWidth*ratio) / 2;
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