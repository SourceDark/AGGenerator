agbotApp.controller('algorithmsCtrl', function ($scope, $http) {
    $scope.algorithms = {};
    $http.get('/api/algorithms').success(function (response) {
        $scope.algorithms = response;
    });
});