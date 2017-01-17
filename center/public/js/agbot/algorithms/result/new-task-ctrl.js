agbotApp.config(function($stateProvider) {
    $stateProvider.state({
        name: 'algorithms.result.newTask',
        url: '/export',
        templateUrl: 'html/algorithms/result/newTask',
        controller: function ($scope, $http, $state, $stateParams, jsonViewerService) {
            $http.get('api/algorithms').success(function (response) {
                $scope.algorithms = response;
            });
            $scope.export = function($analysis_algorithm_id) {
                console.log($analysis_algorithm_id);
                $http
                    .post('/api/algorithms/' + $analysis_algorithm_id + '/tasks/analysis', {task:$scope.task_id})
                    .success(function(response) {
                        $state.go('algorithms.result.tasks');
                    });
            }
        }
    });
});