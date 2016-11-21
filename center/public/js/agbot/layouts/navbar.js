agbotApp.controller('navbarCtrl', function ($scope, $http, $state) {
    $state.go('vision');
});

agbotApp.config(function($stateProvider) {
    $stateProvider.state({
        name: 'aboutUs',
        url: '/aboutUs',
        templateUrl: 'html/aboutUs'
    });
});