/**
 * Created by Nettle on 2017/2/22.
 */

(function () {
    'use strict';

    angular.module('BlurAdmin.pages.scan', [
        'BlurAdmin.pages.scan.new'
    ])
        .config(routeConfig)
        .controller('scanCtrl', function($scope,$state,$http) {
            $scope.networkId = 1;
            $scope.apiUrl = 'http://162.105.30.200:9016';

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
            .state('scan', {
                url: '/scan',
                templateUrl: 'app/pages/scan/scan.html',
                title: '扫描',
                sidebarMeta: {
                    icon: 'ion-search',
                    order: 3
                }
            })
    }

})();
