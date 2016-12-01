agbotApp.config(function($stateProvider) {
    $stateProvider.state({
        name: 'algorithms.algorithmResult',
        url: '/{algorithm_id:[0-9]+}/results/{result_id:[0-9]+}',
        templateUrl: 'html/algorithms/algorithmResult',
        controller: function ($scope, $http, $stateParams, jsonViewerService) {
            $scope.algorithm = {};
            $scope.result = {};
            $scope.algorithm_id = $stateParams.algorithm_id;
            $scope.result_id = $stateParams.result_id;

            $http.get('/api/algorithms/' + $stateParams.algorithm_id)
                .success(function (response)  {
                    $scope.algorithm = response;
                });
            $http.get('/api/algorithms/' + $stateParams.algorithm_id + '/results/'+ $stateParams.result_id)
                .success(function (response) {
                    $scope.result = response;
                    $scope.content = JSON.parse($scope.result.content);
                    jsonViewerService.jsonString = $scope.result.content;
                    jsonViewerService.parseJson();
                });
        }
    });
});