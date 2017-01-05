agbotApp.config(function($stateProvider) {
    $stateProvider.state({
        name: 'task.info',
        url: '/',
        templateUrl: 'html/tasks/task/info',
        controller: function ($scope, $http, $stateParams) {
            $scope.$watch(
                'task', function() {
                    if ($scope.task != null) {
                        if ($scope.task.input != null) {
                            $scope.isInputJson = true;
                            try {
                                $scope.input = JSON.parse($scope.task.input);
                            } catch (e) {
                                $scope.isInputJson = false;
                                $scope.input = $scope.task.input;
                            }
                        }
                        if ($scope.task.output != null) {
                            $scope.isOutputJson = true;
                            try {
                                $scope.output = JSON.parse($scope.task.output);
                            } catch (e) {
                                $scope.isOutputJson = false;
                                $scope.output = $scope.task.output;
                            }
                        }
                        if ($scope.task.errorStack != null) {
                            $scope.isErrorStackJson = true;
                            try {
                                $scope.errorStack = JSON.parse($scope.task.errorStack);
                            } catch (e) {
                                $scope.isErrorStackJson = false;
                                $scope.errorStack = $scope.task.errorStack;
                            }
                        }
                    }
                }
            );
        }
    });
});