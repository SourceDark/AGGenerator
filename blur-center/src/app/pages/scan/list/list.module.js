/**
 * Created by Nettle on 2017/3/13.
 */

(function () {
    'use strict';

    angular.module('BlurAdmin.pages.scan.list', ['ui.select'])
        .config(routeConfig)
        .controller('scanListCtrl', function ($scope, $http, $state) {
            $http
                .get([$scope.apiUrl, 'server', $scope.networkId, 'tasks'].join('/'))
                .then(function (result) {
                    $scope.tasks = result.data;
                }, function () {

                });

            $scope.showLong = function (start, end) {
                if (!end) return ;
                var t = (end - start) / 1000;
                var ret = (t % 60) + 's';
                if ((parseInt(t / 60) % 60) > 0)
                    ret = (parseInt(t / 60) % 60) + 'm' + ret;
                if (parseInt(t / 3600) > 0)
                    ret = parseInt(t / 3600) + 'h' + ret;
                return ret;
            }
        });

    /** @ngInject */
    function routeConfig($stateProvider) {
        $stateProvider
            .state('scan.list', {
                url: '/list',
                templateUrl: 'app/pages/scan/list/list.html',
                title: '扫描'
            });
    }

})();
