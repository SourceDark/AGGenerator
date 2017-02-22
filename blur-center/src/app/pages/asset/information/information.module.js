/**
 * Created by Nettle on 2017/2/22.
 */

(function () {
    'use strict';

    angular.module('BlurAdmin.pages.asset.information', [])
        .config(routeConfig);

    /** @ngInject */
    function routeConfig($stateProvider) {
        $stateProvider
            .state('asset.information', {
                url: '/information',
                templateUrl: 'app/pages/asset/information/information.html',
                title: '资产详情'
            });
    }

})();