agbotApp.config(function($stateProvider) {
    $stateProvider.state({
        name: 'algorithms.result.tasks',
        url: '/tasks',
        templateUrl: 'html/algorithms/result/tasks',
        controller: function ($scope, $http, $stateParams, jsonViewerService) {
        }
    });
});