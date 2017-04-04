/**
 * Created by Nettle on 2017/4/4.
 */

(function () {
    'use strict';

    angular.module('BlurAdmin.pages.attackGraph.path', [])
        .config(routeConfig)
        .controller('pathCtrl', function ($scope, $http, $state) {
        });

    /** @ngInject */
    function routeConfig($stateProvider) {
        $stateProvider
            .state('attackGraph.path', {
                url: '/path',
                templateUrl: 'app/pages/attackGraph/path/path.html',
                title: '路径分析'
            });
    }

})();
