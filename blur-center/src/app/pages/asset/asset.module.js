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
        .config(routeConfig);

    /** @ngInject */
    function routeConfig($stateProvider) {
        $stateProvider
            .state('asset', {
                url: '/asset',
                template : '<ui-view  autoscroll="true" autoscroll-body-top></ui-view>',
                abstract: true,
                title: '资产',
                sidebarMeta: {
                    icon: 'ion-cash',
                    order: 2
                }
            });
    }

})();
