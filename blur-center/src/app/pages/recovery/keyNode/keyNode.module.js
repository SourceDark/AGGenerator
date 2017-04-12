/**
 * Created by Nettle on 2017/4/11.
 */

(function () {
    'use strict';

    angular.module('BlurAdmin.pages.recovery.keyNode', [])
        .config(routeConfig)
        .controller('kyeNodeCtrl', function ($scope, $http, $state) {

        });

    /** @ngInject */
    function routeConfig($stateProvider) {
        $stateProvider
            .state('recovery.keyNode', {
                url: '/keyNode',
                templateUrl: 'app/pages/recovery/keyNode/keyNode.html',
                title: '关键节点',
                sidebarMeta: {
                    order: 1,
                }
            });
    }

})();
