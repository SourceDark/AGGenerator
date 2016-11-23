agbotApp.controller('navbarCtrl', function ($scope, $http, $state) {
    if ($state.current.name == "") {
        $state.go("algorithms.index");
    }
    $scope.inState = function($prefix) {
        return $state.current.name.indexOf($prefix) == 0;
    }
});

agbotApp.config(function($stateProvider) {
    $stateProvider.state({
        name: 'aboutUs',
        url: '/aboutUs',
        templateUrl: 'html/aboutUs'
    });
});