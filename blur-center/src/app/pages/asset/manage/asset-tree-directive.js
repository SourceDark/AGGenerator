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
                    if (host.gateway && $scope.ipPool[host.gateway] && host.outer_interface != 'external_root') {
                        host.father = $scope.ipPool[host.gateway].id;
                        $scope.idPool[host.father].child.push($scope.idPool[host.id]);
                        $scope.idPool[host.father].isRouter = true;
                        $scope.links.push({
                            target: $scope.idPool[host.id],
                            source: $scope.idPool[host.father]
                        });
                    }
                    if (host.outer_interface == 'external_root')
                        $scope.root = [$scope.idPool[host.id]];
                });

                function count(host) {
                    if (host.child.length) {
                        host.importantAsset = 0;
                        host.vulnerabilities = 0;
                        host.nodeCount = 0;
                        host.child.forEach(function (chd) {
                            count(chd);
                            host.importantAsset += chd.importantAsset;
                            host.vulnerabilities += chd.vulnerabilities;
                            host.nodeCount += chd.nodeCount;
                        });
                    }   else {
                        host.importantAsset = (host.value >= 8 ? 1 : 0);
                        host.vulnerabilities = host.vulnerabilityCount;
                        host.nodeCount = 1;
                    }
                }
                count($scope.root[0]);

                $scope.data.forEach(function (host) {
                    if (host.father && host.isRouter)
                        $scope.idPool[host.father].router_child = true;
                    host.expend = false;
                    var safeScore = host.score * (host.child.length ? 1 : 10);
                    host.safeLevel = 1;
                    if (safeScore > 10) host.safeLevel = 2;
                    if (safeScore > 60) host.safeLevel = 3;
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

                scope.getText = function(value) {
                    if (value == 1) return '优';
                    if (value == 2) return '良';
                    return '差';
                };

                function render() {
                    var template = '<div class="node" ng-class="{true: \'no-child\'}[!node.router_child] "ng-repeat="node in ' + attrs.assetNode + ' | filter:{isRouter:true}">\
                        <div class="node-main">\
                            <div class="node-card">\
                                <div class="node-card-header">{{node.inner_interface}}</div>\
                                <div class="node-card-content">\
                                    <div class="node-card-content-left">\
                                        <p>{{node.nodeCount}}个结点</p>\
                                        <p>{{node.vulnerabilities}}个漏洞</p>\
                                        <p>{{node.importantAsset}}个重要资产</p>\
                                    </div>\
                                    <div class="node-card-content-right">\
                                        <p class="score" ng-class="{1: \'good\', 2: \'normal\', 3: \'bad\'}[node.safeLevel]">{{node.score | number: 0}}</p>\
                                        <p>安全状况：{{getText(node.safeLevel)}}</p>\
                                    </div>\
                                </div>\
                                <div class="node-card-footer">\
                                    <div class="node-card-footer-btn" ui-sref="asset.information({sensorName:node.sensorName, ip:node.inner_interface})"><i class="ion-document-text"></i></div>\
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
                                                <p>{{host.vulnerabilityCount || "0"}}个漏洞</p>\
                                                <p style="font-weight: bor">资产价值：{{host.value || "0"}}</p>\
                                                <p ng-class="{1: \'good\', 2: \'normal\', 3: \'bad\'}[host.safeLevel]">威胁评分：{{host.score | number:1}}</p>\
                                            </div>\
                                            <div class="host-card-content-footer">\
                                                <div class="host-card-content-footer-btn" ui-sref="asset.information({sensorName:host.sensorName, ip:host.inner_interface})"><i class="ion-document-text"></i></div>\
                                                <div class="host-card-content-footer-btn"><i class="ion-edit"></i></div>\
                                            </div>\
                                        </div>\
                                    </div>\
                                </div>\
                                <div ng-if="!node.expend" class="host-list">\
                                    <div class="child-content-header">{{node.inner_interface}}</div>\
                                    <table>\
                                        <tbody>\
                                            <tr ng-repeat="host in node.child | filter:{isRouter:false} | limitTo: 5" ng-class="{1: \'good\', 2: \'normal\', 3: \'bad\'}[host.safeLevel]">\
                                                <td>{{host.inner_interface}}</td>\
                                                <td>{{host.vulnerabilityCount || "0"}}个漏洞</td>\
                                                <td>威胁评分{{host.score | number:1}}</td>\
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