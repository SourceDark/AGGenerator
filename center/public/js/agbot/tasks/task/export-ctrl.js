agbotApp.config(function($stateProvider) {
    $stateProvider.state({
        name: 'task.export',
        url: '/export',
        templateUrl: 'html/tasks/task/export',
        controller: function ($scope, $http, $state, $stateParams, jsonViewerService) {
            $http.get('api/algorithms?inputType=' + $scope.task.outputType).success(function (response) {
                $scope.algorithms = response;
            });
            $scope.export = function($analysis_algorithm_id) {
                $http
                    .post('/api/algorithms/' + $analysis_algorithm_id + '/tasks/analysis', {task:$scope.task.id})
                    .success(function(response) {
                        $state.go('tasks');
                    });
            }
        }
    });
});