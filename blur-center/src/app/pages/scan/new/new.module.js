/**
 * Created by Nettle on 2017/2/22.
 */

(function () {
    'use strict';

    angular.module('BlurAdmin.pages.scan.new', [])
        .config(routeConfig);

    /** @ngInject */
    function routeConfig($stateProvider) {
        $stateProvider
            .state('scan.new', {
                url: '/new',
                templateUrl: 'app/pages/scan/new/new.html',
                title: '新建扫描任务'
            });
    }

})();