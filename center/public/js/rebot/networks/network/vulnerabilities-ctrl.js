agbotApp.config(function($stateProvider) {
    $stateProvider.state({
        name: 'network.vulnerabilities',
        url: '/vulnerabilities',
        templateUrl: 'html/networks/network/vulnerabilities',
        controller: function ($scope, $http, $stateParams) {
        }
    });
});