/**
 * Created by Nettle on 2017/4/4.
 */

(function () {
    'use strict';

    angular.module('BlurAdmin.pages.attackGraph.path', [])
        .config(routeConfig)
        .controller('pathCtrl', function ($scope, $http, $state) {
            // $scope.idPool = {};
            $scope.data.nodes.forEach(function (node) {
                if (node.attacker) node.type = 'OR';
                // $scope.idPool[ node.id ] = node;
                node.conditions = [];
            });
            $scope.data.edges.forEach(function (edge) {
               // edge.target = $scope.idPool[ edge.target ];
               // edge.source = $scope.idPool[ edge.source ];
                edge.target.conditions.push(edge.source);
            });
            $scope.showPath = function(index) {
                $scope.loading = true;
                $scope.selectedPathId = index;
                $scope.data.nodes.forEach(function (node) {
                    node.inPath = false;
                    node.inSeq = false;
                });
                $scope.paths[index].forEach(function (id) {
                    $scope.idPool[id].inPath = true;
                });
                $scope.root = $scope.idPool[1];
                $scope.seq = [$scope.root];
                for (var i = 0; i < $scope.seq.length; ++i) {
                    var u = $scope.seq[i];
                    u.inSeq = true;
                    $scope.data.edges.forEach(function (edge) {
                        if (edge.target.id == u.id && edge.source.inPath && !edge.source.inSeq)
                            $scope.seq.push(edge.source);
                    });
                }
                $scope.seq = $scope.seq.slice().reverse();
                $scope.loading = false;
            };
            $scope.showPath(0);
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
