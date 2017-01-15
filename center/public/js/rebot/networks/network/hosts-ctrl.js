agbotApp.config(function($stateProvider) {
    $stateProvider.state({
        name: 'network.hosts',
        url: '/hosts',
        templateUrl: 'html/networks/network/hosts',
        controller: function ($scope, $http, $stateParams) {
        }
    });
});