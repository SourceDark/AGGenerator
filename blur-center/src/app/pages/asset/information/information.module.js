/**
 * Created by Nettle on 2017/2/22.
 */

(function () {
    'use strict';

    angular.module('BlurAdmin.pages.asset.information', [])
        .config(routeConfig)
        .controller('assetInformationCtrl', function ($scope, $stateParams, $http) {
            console.log($stateParams);
            $http
                .get([$scope.apiUrl, 'server', $scope.networkId, 'sensors', $stateParams.sensorName, 'hosts', $stateParams.ip].join('/'))
                .then(function (result) {
                    console.log(result.data);
                }, function (result) {
                    console.error('获取资产信息失败');
                });
        });

    /** @ngInject */
    function routeConfig($stateProvider) {
        $stateProvider
            .state('asset.information', {
                url: '/information',
                params:{'sensorName':null,ip:null},
                templateUrl: 'app/pages/asset/information/information.html',
                title: '资产详情'
            });
    }

})();