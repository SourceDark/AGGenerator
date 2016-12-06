agbotApp.controller('algorithmCtrl', function ($scope, $http, $stateParams) {
    $scope.algorithm = {};
    $scope.results = {};
    $scope.algorithm_id = $stateParams.algorithm_id;
    $http.get('/api/algorithms/' + $stateParams.algorithm_id)
        .success(function (response)  {
            $scope.algorithm = response;
        });
    $http.get('/api/algorithms/' + $stateParams.algorithm_id + '/results')
        .success(function (response) {
            $scope.results = response;
        });
});