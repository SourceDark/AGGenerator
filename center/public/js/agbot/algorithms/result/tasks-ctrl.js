agbotApp.config(function($stateProvider) {
    $stateProvider.state({
        name: 'algorithms.result.tasks',
        url: '/tasks',
        templateUrl: 'html/algorithms/result/tasks',
        controller: function ($scope, $http, $stateParams, jsonViewerService) {
            $http.get('/api/algorithms/' + $stateParams.algorithm_id + '/results/'+ $stateParams.result_id)
                .success(function (response) {
                    $scope.result = response;
                });
        }
    });
});