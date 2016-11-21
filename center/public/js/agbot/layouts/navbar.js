agbotApp.controller('navbarCtrl', function ($scope, $http) {

});

agbotApp.config(function($stateProvider) {
    $stateProvider.state({
        name: 'aboutUs',
        url: '/aboutUs',
        templateUrl: 'html/aboutUs'
    });
});