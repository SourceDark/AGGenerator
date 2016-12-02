agbotApp.config(function($stateProvider) {
    $stateProvider.state({
        name: 'algorithms',
        url: '/algorithms',
        template: '<ui-view></ui-view>',
        controller: function($scope, $state, $http) {
            $http.get('/api/algorithms').success(function (response) {
                $scope.algorithms = response;
            });
            if ($state.current.name == 'algorithms') {
                $state.go('algorithms.index');
            }
        }
    });
    $stateProvider.state({
        name: 'algorithms.index',
        url: '/',
        templateUrl: 'html/algorithms'
    });
    $stateProvider.state({
        name: 'algorithms.algorithm',
        url: '/{algorithm_id:[0-9]+}',
        templateUrl: 'html/algorithms/algorithm'
    });
});