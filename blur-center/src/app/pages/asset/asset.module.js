/**
 * Created by Nettle on 2017/3/22.
 */

(function () {
    'use strict';

    angular.module('BlurAdmin.pages.asset', [
        'BlurAdmin.pages.asset.information',
        'BlurAdmin.pages.asset.list',
        'BlurAdmin.pages.asset.manage'
    ])
        .config(routeConfig)
        .controller('assetCtrl', function($scope) {
            $scope.networkId = 1;
            $scope.apiUrl = 'http://162.105.30.200:9016';
        });

    /** @ngInject */
    function routeConfig($stateProvider) {
        $stateProvider
            .state('asset', {
                url: '/asset',
                template : '<ui-view ng-controller="assetCtrl" autoscroll="true" autoscroll-body-top></ui-view>',
                abstract: true,
                title: '资产',
                sidebarMeta: {
                    icon: 'ion-cash',
                    order: 2
                }
            });
    }

})();
