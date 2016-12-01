agbotApp.config(function($stateProvider) {
    $stateProvider.state({
        name: 'algorithms.result',
        url: '/{algorithm_id:[0-9]+}/results/{result_id:[0-9]+}',
        templateUrl: 'html/algorithms/result',
        controller: function ($scope, $http, $state, $stateParams, jsonViewerService) {
            $scope.algorithm_id = $stateParams.algorithm_id;
            $scope.result_id = $stateParams.result_id;

            $scope.algorithm = {};
            $http.get('/api/algorithms/' + $stateParams.algorithm_id)
                .success(function (response)  {
                    $scope.algorithm = response;
                });
            $http.get('/api/algorithms/' + $stateParams.algorithm_id + '/results/'+ $stateParams.result_id)
                .success(function (response) {
                    $scope.result = response;
                });

            if ($state.current.name == "algorithms.result") {
                $state.go('algorithms.result.info');
            }
        }
    });
});