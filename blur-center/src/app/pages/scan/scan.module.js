/**
 * Created by Nettle on 2017/2/22.
 */

(function () {
    'use strict';

    angular.module('BlurAdmin.pages.scan', [
        'BlurAdmin.pages.scan.new',
        'BlurAdmin.pages.scan.list'
    ])
        .run(['$rootScope', '$state', function($rootScope, $state) {
            $rootScope.$on('$stateChangeStart', function(evt, to, params) {
                if (to.redirectTo) {
                    evt.preventDefault();
                    $state.go(to.redirectTo, params, {location: 'replace'})
                }
            });
        }])
        .config(routeConfig)
        .controller('scanCtrl', function($scope,$state,$http) {
            $scope.networkId = 1;
            $scope.apiUrl = 'http://162.105.30.200:9016';

            $state.go('scan.list');
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
                },
                redirectTo: 'scan.list'
            });
    }

})();
