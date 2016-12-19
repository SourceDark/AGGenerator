agbotApp.controller('algorithmCtrl', function ($scope, $http, $stateParams) {
    $scope.algorithm = {};
    $scope.results = {};
    $scope.algorithm_id = $stateParams.algorithm_id;
    $http.get('/api/algorithm?uri=/algorithms/' + $stateParams.algorithm_id)
        .success(function (response)  {
            $scope.algorithm = response;
        });
    $http.get('/api/algorithm?uri=/algorithms/' + $stateParams.algorithm_id + '/tasks')
        .success(function (response) {
            $scope.tasks = response;
        });
});