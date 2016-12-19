agbotApp.config(function($stateProvider) {
    $stateProvider.state({
        name: 'algorithms.result.info',
        url: '/info',
        templateUrl: 'html/algorithms/result/info',
        controller: function ($scope, $http, $stateParams, jsonViewerService) {
            $scope.$watch(
                'task', function() {
                    if ($scope.task != null) {
                        jsonViewerService.jsonString = $scope.task.output || $scope.task.errorStack;
                        jsonViewerService.parseJson();
                    }
                }
            );
        }
    });
});