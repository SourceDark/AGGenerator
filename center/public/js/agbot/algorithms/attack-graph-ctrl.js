/**
 * Created by Nettle on 2016/11/25.
 */

agbotApp.config(['$stateProvider', function($stateProvider) {
    $stateProvider.state('algorithm_attack_graph', {
        url: '/algorithms/{algorithm_id:[0-9]+}/attack_graph/{id:[0-9]+}',
        templateUrl: '/html/algorithms/attack_graph',
        controller: 'algorithmAttackGraphCtrl'
    });
}]).controller('algorithmAttackGraphCtrl', ['$scope', '$stateParams', '$http', '$q', function($scope, $stateParams, $http, $q) {
    // console.log($stateParams.id);
    $scope.id = $stateParams.id;
    $scope.algorithm_id = $stateParams.algorithm_id;
    var request = [
        $http.get(['api','algorithms',$scope.algorithm_id].join('/')),
        $http.get(['api','algorithms',$scope.algorithm_id,'results',$scope.id].join('/'))
    ];
    $q.all(request).then( function (result) {
        if (result[0].data.type == 1) {
            $scope.analysis = JSON.parse(result[1].data.content);
            $scope.paths = $scope.analysis.PathList;
            $http.get(['api','algorithms',$scope.analysis.input.algorithm_id,'results',$scope.analysis.input.result_id].join('/'))
                .then(function (result) {
                    $scope.nodes = JSON.parse(result.data.content).nodes;
                    $scope.links = JSON.parse(result.data.content).edges;
                });
        }   else {
            $scope.nodes = JSON.parse(result[1].data.content).nodes;
            $scope.links = JSON.parse(result[1].data.content).edges;
        }
    });
}]);