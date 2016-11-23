agbotApp.controller('algorithmCtrl', function ($scope, $http, $stateParams) {
    $scope.algorithm = {};
    $scope.results = {};
    $http.get('/api/algorithms/' + $stateParams.algorithm_id)
        .success(function (response)  {
            $scope.algorithm = response;
        });
    $http.get('/api/algorithms/' + $stateParams.algorithm_id + '/results')
        .success(function (response) {
            console.log(response);
            $scope.results = response;
        });
});