/**
 * Created by Nettle on 2017/4/11.
 */

(function () {
    'use strict';

    angular.module('BlurAdmin.pages.recovery.impVul', [])
        .config(routeConfig)
        .controller('impVulCtrl', function ($scope, $http, $state) {
            $scope.impVul = [
                {id: 'CVE-2015-2617',times:1,paths:2,asset:3,score:20.0},
                {id: 'CVE-2015-2639',times:1,paths:1,asset:1,score:15.0},
                {id: 'CVE-2015-4830',times:1,paths:1,asset:1,score:12.0}
            ]
        });

    /** @ngInject */
    function routeConfig($stateProvider) {
        $stateProvider
            .state('recovery.impVul', {
                url: '/importantVulnerabilities',
                templateUrl: 'app/pages/recovery/importantVulnerabilities/importantVulnerabilities.html',
                title: '重要漏洞',
                sidebarMeta: {
                    order: 2,
                }
            });
    }

})();
