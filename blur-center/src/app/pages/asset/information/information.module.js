/**
 * Created by Nettle on 2017/2/22.
 */

(function () {
    'use strict';

    angular.module('BlurAdmin.pages.asset.information', [])
        .config(routeConfig)
        .controller('assetInformationCtrl', function ($scope, $stateParams, $http, $window) {
            console.log($stateParams);
            $http
                .get([$scope.apiUrl, 'server', $scope.networkId, 'sensors', $stateParams.sensorName, 'hosts', $stateParams.ip].join('/'))
                .then(function (result) {
                    $scope.host = result.data;
                }, function (result) {
                    console.error('获取资产信息失败');
                });

            $scope.goBack = function () {
                $window.history.back();
            };

        }).filter('cut', function () {
        return function (value, wordwise, max, tail) {
            if (!value) return '';

            max = parseInt(max, 10);
            if (!max) return value;
            if (value.length <= max) return value;

            value = value.substr(0, max);
            if (wordwise) {
                var lastspace = value.lastIndexOf(' ');
                if (lastspace != -1) {
                    value = value.substr(0, lastspace);
                }
            }

            return value + (tail || ' …');
        };
    }).filter('unique', function() {
        return function (collection, keyname) {
            var output = [],
                keys = [];

            angular.forEach(collection, function (item) {
                var key = item[keyname];
                if (keys.indexOf(key) === -1) {
                    keys.push(key);
                    output.push(item);
                }
            });

            return output;
        }
    });

    /** @ngInject */
    function routeConfig($stateProvider) {
        $stateProvider
            .state('asset.information', {
                url: '/information/{sensorName}/{ip}',
                // params:{'sensorName':null,ip:null},
                templateUrl: 'app/pages/asset/information/information.html',
                title: '资产详情'
            });
    }

})();;