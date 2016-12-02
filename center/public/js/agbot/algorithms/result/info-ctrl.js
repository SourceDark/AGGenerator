agbotApp.config(function($stateProvider) {
    $stateProvider.state({
        name: 'algorithms.result.info',
        url: '/info',
        templateUrl: 'html/algorithms/result/info',
        controller: function ($scope, $http, $stateParams, jsonViewerService) {
            $scope.$watch(
                'result', function() {
                    if ($scope.result != null) {
                        jsonViewerService.jsonString = $scope.result.content;
                        jsonViewerService.parseJson();
                    }
                }
            );
        }
    });
});