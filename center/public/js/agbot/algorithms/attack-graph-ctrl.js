/**
 * Created by Nettle on 2016/11/25.
 */

agbotApp.config(['$stateProvider', function($stateProvider) {
    $stateProvider.state('algorithm_attack_graph', {
        url: '/algorithms/{algorithm_id:[0-9]+}/attack_graph/{id:[0-9]+}',
        templateUrl: '/html/algorithms/attack_graph',
        controller: 'algorithmAttackGraphCtrl'
    });
}]).controller('algorithmAttackGraphCtrl', ['$scope', '$stateParams', '$http', function($scope, $stateParams, $http) {
    // console.log($stateParams.id);
    $scope.id = $stateParams.id;
    $scope.algorithm_id = $stateParams.algorithm_id;
}]);