/**
 * Created by Nettle on 2017/2/22.
 */

(function () {
    'use strict';

    angular.module('BlurAdmin.pages.asset.list', [
    ])
        .config(routeConfig)
        .controller('assetListCtrl', function($scope,$state,$http) {
            $scope.networkId = 1;
            $scope.apiUrl = 'http://162.105.30.200:9016';

            $scope.sensorCondition = {};
            $scope.valueCondition = 1;

            $scope.valueComparator = function (expected, actual) {
                return actual <= expected;
            };

            $http
                .get([$scope.apiUrl, 'server', $scope.networkId, 'hosts', 'network'].join('/'))
                .then(function (result) {
                    $scope.assets = result.data;
                    $scope.sensors = $.unique(result.data.map(function (host) {
                        return host.sensorName;
                    }));
                    $scope.values = $.unique([1].concat(result.data.map(function (host) {
                        if (!host.value) host.value = 1;
                        return host.value;
                    })));
                    $scope.assets.forEach(function (host) {
                        var safeScore = host.score * (host.outer_interface == "" ? 10 : 1);
                        host.safeLevel = 1;
                        if (safeScore > 10) host.safeLevel = 2;
                        if (safeScore > 60) host.safeLevel = 3;
                    });
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

            $scope.setValueCondition = function (value) {
                $scope.valueCondition = value;
            }
        });

    /** @ngInject */
    function routeConfig($stateProvider) {

        $stateProvider
            .state('asset.list', {
                url: '/list',
                templateUrl: 'app/pages/asset/list/list.html',
                title: '资产列表',
                sidebarMeta: {
                    order: 1,
                }
            });
    }

})();
