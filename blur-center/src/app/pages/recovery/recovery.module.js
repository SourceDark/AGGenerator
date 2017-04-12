/**
 * Created by Nettle on 2017/4/11.
 */

(function () {
    'use strict';

    angular.module('BlurAdmin.pages.recovery', [
        'BlurAdmin.pages.recovery.impVul',
        'BlurAdmin.pages.recovery.keyNode'
    ])
        .config(routeConfig)
        .controller('recoveryCtrl', function($scope,$state,$http) {
            $scope.networkId = 1;
            $scope.apiUrl = 'http://162.105.30.200:9016';

            $scope.data = JSON.parse(attack_graph_test_data.input);
            $scope.paths = JSON.parse(attack_graph_test_data.output);
            $scope.keyNodes = [13,15,17];

            $scope.idPool = {};
            $scope.data.nodes.forEach(function (node) {
                $scope.idPool[ node.id ] = node;
            });
            $scope.data.edges.forEach(function (edge) {
                edge.target = $scope.idPool[ edge.target ];
                edge.source = $scope.idPool[ edge.source ];
            });

            console.log($scope.data);
            console.log($scope.paths);
        });

    /** @ngInject */
    function routeConfig($stateProvider) {
        $stateProvider
            .state('recovery', {
                url: '/recovery',
                template: '<div ui-view="" ng-controller="recoveryCtrl"></div>',
                title: '修复策略',
                sidebarMeta: {
                    icon: 'ion-settings',
                    order: 6
                }
            });
    }

})();