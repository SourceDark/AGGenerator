/**
 * Created by Nettle on 2017/2/22.
 */

(function () {
    'use strict';

    angular.module('BlurAdmin.pages.scan.new', ['ui.select'])
        .config(routeConfig)
        .controller('newScanCtrl', function ($scope, $http, $state) {
            $scope.newTask = {
                sensor: null,
                ip: ''
            };

            $scope.sensors = [
                {name: 'xr-test', id: 0}, {name: 'xr-test-2', id: 1}
            ];

            $scope.submit = function () {
                $http({
                    method  : 'POST',
                    url     : [$scope.apiUrl, 'server', $scope.networkId, 'tasks'].join('/'),
                    data    : $.param({
                        ip: $scope.newTask.ip,
                        sensor: $scope.newTask.sensor.name
                    }),  // pass in data as strings
                    headers : { 'Content-Type': 'application/x-www-form-urlencoded' }  // set the headers so angular passing info as form data (not request payload)
                }).then(function (result) {
                        $state.go('scan');
                    }, function () {

                    });
            }
        });

    /** @ngInject */
    function routeConfig($stateProvider) {
        $stateProvider
            .state('scan.new', {
                url: '/new',
                templateUrl: 'app/pages/scan/new/new.html',
                title: '新建扫描任务'
            });
    }

})();