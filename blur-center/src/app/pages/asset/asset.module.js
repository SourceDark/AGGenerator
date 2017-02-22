/**
 * Created by Nettle on 2017/2/22.
 */

(function () {
    'use strict';

    angular.module('BlurAdmin.pages.asset', [
        'BlurAdmin.pages.asset.information'
    ])
        .config(routeConfig)
        .controller('assetCtrl', function($scope,$state) {
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
