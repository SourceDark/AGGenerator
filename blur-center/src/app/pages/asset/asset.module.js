/**
 * Created by Nettle on 2017/2/22.
 */

(function () {
    'use strict';

    angular.module('BlurAdmin.pages.asset', [
        'BlurAdmin.pages.asset.information'
    ])
        .config(routeConfig)
        .controller('assetCtrl', function($scope,$state,$http) {
            $scope.networkId = 1;
            $scope.apiUrl = 'http://162.105.30.200:9016';

            $http
                .get([$scope.apiUrl, 'server', $scope.networkId, 'hosts'].join('/'))
                .then(function (result) {
                    $scope.sensorNames = $.unique(result.data.map(function (host) {
                        return host.sensorName;
                    }));
                    $scope.hosts = {};
                    $scope.sensorNames.forEach(function (sensor) {
                        $scope.hosts[ sensor ] = result.data.filter(function (host) {
                            return host.sensorName == sensor;
                        })
                    });
                }, function (result) {
                    console.error('获取资产信息失败');
                });
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
