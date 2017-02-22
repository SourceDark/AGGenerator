/**
 * Created by Nettle on 2017/2/22.
 */

(function () {
    'use strict';

    angular.module('BlurAdmin.pages.scan', [
        'BlurAdmin.pages.scan.new'
    ])
        .config(routeConfig)
        .controller('scanCtrl', function($scope,$state) {
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
