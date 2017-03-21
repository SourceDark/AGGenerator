/**
 * Created by Nettle on 2017/2/22.
 */

(function () {
    'use strict';

    angular.module('BlurAdmin.pages.asset', [
        'BlurAdmin.pages.asset.information',
        'BlurAdmin.pages.asset.manage'
    ])
        .config(routeConfig)
        .controller('assetCtrl', function($scope,$state,$http) {
            $scope.networkId = 1;
            $scope.apiUrl = 'http://162.105.30.200:9016';

            $scope.sensorCondition = {};
            $scope.valueCondition = 1;

            $scope.valueComparator = function (expected, actual) {
                return actual >= expected;
            };

            $http
                .get([$scope.apiUrl, 'server', $scope.networkId, 'hosts'].join('/'))
                .then(function (result) {
                    $scope.assets = result.data;
                    $scope.sensors = $.unique(result.data.map(function (host) {
                        return host.sensorName;
                    }));
                    $scope.values = $.unique(result.data.map(function (host) {
                        if (!host.value) host.value = 1;
                        return host.value;
                    }));
                }, function (result) {
                    console.error('获取资产信息失败');
                });

            $scope.setSensorCondition = function (sensor) {
                if (!sensor)
                    $scope.sensorCondition = {};
                else
                    $scope.sensorCondition = {
                        sensorName: sensor
                    };
            }
        });

    /** @ngInject */
    function routeConfig($stateProvider) {
        $stateProvider
            .state('asset', {
                url: '/asset',
                templateUrl: 'app/pages/asset/asset.html',
                title: '资产',
                sidebarMeta: {
                    icon: 'ion-cash',
                    order: 2
                }
            })
    }

})();
