agbotApp.config(function($stateProvider) {
    $stateProvider.state({
        name: 'network.info',
        url: '/info',
        templateUrl: 'html/networks/network/info',
        controller: function ($scope, $http, $stateParams) {
        }
    });
});