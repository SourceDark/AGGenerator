/**
 * Created by Nettle on 2017/4/4.
 */

(function () {
    'use strict';

    angular.module('BlurAdmin.pages.attackGraph', [
        'BlurAdmin.pages.attackGraph.graph',
        'BlurAdmin.pages.attackGraph.path'
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
        .controller('attackGraphCtrl', function($scope,$state,$http) {
            $scope.networkId = 1;
            $scope.apiUrl = 'http://162.105.30.200:9016';
        });

    /** @ngInject */
    function routeConfig($stateProvider) {
        $stateProvider
            .state('attackGraph', {
                url: '/attack_graph',
                template: '<div ui-view="" ng-controller="attackGraphCtrl"></div>',
                title: '攻击图',
                sidebarMeta: {
                    icon: 'ion-network',
                    order: 5
                },
                redirectTo: 'attackGraph.graph'
            });
    }

})();
