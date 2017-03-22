/**
 * Created by Nettle on 2017/3/22.
 */

angular.module('BlurAdmin.pages.asset.manage')
    .directive('assetTree', function () {
        return {
            restrict: 'A',
            scope: {
                data: '=assetTree',
                completed: '='
            },
            replace: true,
            template: '<div asset-node="root" class="asset-tree"></div>',
            controller: ['$scope', function ($scope, attrs) {
                // init tree
                $scope.idPool = {};
                $scope.ipPool = {};
                for (var i in $scope.data) {
                    $scope.data[i].id = parseInt(i) + 1;
                    $scope.idPool[ $scope.data[i].id ] = $scope.data[i];
                    $scope.ipPool[ $scope.data[i].inner_interface] = $scope.data[i];
                    if ($scope.data[i].outer_interface)
                        $scope.ipPool[ $scope.data[i].outer_interface] = $scope.data[i];
                    $scope.data[i].child = [];
                    $scope.data[i].isRouter = false;
                }

                $scope.links = [];
                $scope.data.forEach(function (host) {
                    host.father = 0;
                    // console.log(host);
                    if (host.gateway && $scope.ipPool[host.gateway]) {
                        host.father = $scope.ipPool[host.gateway].id;
                        $scope.idPool[host.father].child.push($scope.idPool[host.id]);
                        $scope.idPool[host.father].isRouter = true;
                        $scope.links.push({
                            target: $scope.idPool[host.id],
                            source: $scope.idPool[host.father]
                        });
                    }
                    if (host.father == 0)
                        $scope.root = [$scope.idPool[host.id]];
                });


                $scope.data.forEach(function (host) {
                    if (host.father && host.isRouter)
                        $scope.idPool[host.father].router_child = true;
                    host.expend = false;
                });
                // init over
                // $scope.clicked = function () {
                //     $scope.$parent.noStory = false;
                // }
            }]
        };
    })
    .directive('assetNode', ['$compile', function ($compile) {
        return {
            restrict: 'A',
            require: '^assetTree',
            link: function (scope, element, attrs, controller) {
                scope.expend = function (host) {
                    host.expend = !host.expend;
                };

                function render() {
                    var template = '<div class="node" ng-class="{true: \'no-child\'}[!node.router_child] "ng-repeat="node in ' + attrs.assetNode + ' | filter:{isRouter:true}">\
                        <div class="node-main">\
                            <div class="node-card">\
                                <div class="node-card-header">{{node.inner_interface}}</div>\
                                <div class="node-card-content">\
                                    <div class="node-card-content-left">\
                                        <p>123个结点</p>\
                                        <p>66个漏洞</p>\
                                        <p>4个重要资产</p>\
                                    </div>\
                                    <div class="node-card-content-right">\
                                        <p class="score">50</p>\
                                        <p>安全状况：凑合</p>\
                                    </div>\
                                </div>\
                                <div class="node-card-footer">\
                                    <div class="node-card-footer-btn"><i class="ion-document-text"></i></div>\
                                    <div class="node-card-footer-btn"><i class="ion-edit"></i></div>\
                                    <div class="node-card-footer-btn"><i class="ion-more"></i></div>\
                                </div>\
                            </div>\
                            <div class="child-content">\
                                <div ng-if="node.expend" class="host-cards">\
                                    <div ng-repeat="host in node.child | filter:{isRouter:false}" class="host-card">\
                                        <div class="host-card-content">\
                                            <div class="host-card-content-header">{{host.inner_interface}}</div> \
                                            <div class="host-card-content-body">\
                                                <p>12个漏洞</p>\
                                                <p>资产价值：10</p>\
                                                <p>威胁评分：9.5</p>\
                                            </div>\
                                            <div class="host-card-content-footer">\
                                                <div class="host-card-content-footer-btn"><i class="ion-document-text"></i></div>\
                                                <div class="host-card-content-footer-btn"><i class="ion-edit"></i></div>\
                                            </div>\
                                        </div>\
                                    </div>\
                                </div>\
                                <div ng-if="!node.expend" class="host-list">\
                                    <div class="child-content-header">{{node.inner_interface}}</div>\
                                    <table>\
                                        <tbody>\
                                            <tr ng-repeat="host in node.child | filter:{isRouter:false} | limitTo: 5">\
                                                <td>{{host.inner_interface}}</td>\
                                                <td>13个漏洞</td>\
                                                <td>威胁评分9.9</td>\
                                            </tr>\
                                        </tbody>\
                                    </table>\
                                    <div >{{host.inner_interface}}</div>\
                                </div>\
                                <div class="child-content-expend" ng-click="expend(node)"><i ng-class="{true: \'ion-chevron-up\', false: \'ion-chevron-down\'}[node.expend]"></i></div>\
                            </div>\
                        </div>\
                        <div class="child-router" asset-node="node.child"></div>\
                        </div>';
                    element.html('').append($compile(template)(scope));
                }
                render();
            }
        };
    }]);