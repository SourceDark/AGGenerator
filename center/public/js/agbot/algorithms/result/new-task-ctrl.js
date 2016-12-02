agbotApp.config(function($stateProvider) {
    $stateProvider.state({
        name: 'algorithms.result.newTask',
        url: '/export',
        templateUrl: 'html/algorithms/result/newTask',
        controller: function ($scope, $http, $state, $stateParams, jsonViewerService) {
            console.log($scope.algorithms);
            $scope.export = function($analysis_algorithm_id) {
                $http
                    .post('/api/algorithms/' + $scope.algorithm_id + '/results/' + $scope.result_id + '/analysis',
                        {
                            analysis_algorithm_id: $analysis_algorithm_id
                        }
                    )
                    .success(function(response) {
                        $state.go('algorithms.result.tasks');
                    });
            }
        }
    });
});