agbotApp.config(function($stateProvider) {
    $stateProvider.state({
        name: 'task',
        url: '/tasks/{taskId}',
        templateUrl: 'html/tasks/task',
        controller: function ($scope, $http, $stateParams) {
            $scope.taskId = $stateParams.taskId;
            $scope.status = 'loading';
            $http.get('api/tasks/' + $stateParams.taskId)
                .success(function(response) {
                    $scope.task = response;
                    $scope.status = 'list';
                })
                .error(function(response) {
                    $scope.status = 'fail';
                });
        }
    });
});