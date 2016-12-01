agbotApp.config(function($stateProvider) {
    $stateProvider.state({
        name: 'algorithms.result.newTask',
        url: '/newTask',
        templateUrl: 'html/algorithms/result/newTask',
        controller: function ($scope, $http, $stateParams, jsonViewerService) {
        }
    });
});