/**
 * Created by Nettle on 2017/2/22.
 */

(function () {
    'use strict';

    angular.module('BlurAdmin.pages.scan.new', ['ui.select'])
        .config(routeConfig)
        .controller('newScanCtrl', function ($scope, $http) {
            $scope.newTask = {
                sensor: null,
                ip: ''
            };

            $scope.sensors = [
                {name: 'xr-test', id: 0}, {name: 'xr-test-2', id: 1}
            ];
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