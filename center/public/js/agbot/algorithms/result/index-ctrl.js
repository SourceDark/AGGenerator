agbotApp.config(function($stateProvider) {
    $stateProvider.state({
        name: 'algorithms.result',
        url: '/{algorithm_id:[0-9]+}/tasks/{task_id:[0-9]+}',
        templateUrl: 'html/algorithms/result',
        controller: function ($scope, $http, $state, $stateParams, jsonViewerService) {
            $scope.algorithm_id = $stateParams.algorithm_id;
            $scope.task_id = $stateParams.task_id;

            $scope.algorithm = {};
            $http.get('/api/algorithms/' + $stateParams.algorithm_id)
                .success(function (response)  {
                    $scope.algorithm = response;
                });
            $http.get('/api/algorithm?uri=/tasks/' + $stateParams.task_id)
                .success(function (response) {
                    $scope.task = response;
                });

            if ($state.current.name == "algorithms.result") {
                $state.go('algorithms.result.info');
            }
        }
    });
});