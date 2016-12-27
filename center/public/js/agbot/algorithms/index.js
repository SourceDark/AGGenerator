agbotApp.config(function($stateProvider) {
    $stateProvider.state({
        name: 'algorithms',
        url: '/algorithms',
        template: '<ui-view></ui-view>',
        controller: function ($scope, $state) {
            // Auto router
            if ($state.current.name == 'algorithms') {
                $state.go('algorithms.index');
            }
        }
    });
});

agbotApp.config(function($stateProvider) {
    $stateProvider.state({
        name: 'algorithms.index',
        url: '/',
        templateUrl: 'html/algorithms',
        controller: function ($scope, $http) {
            $scope.algorithms = null;
            $http.get('api/algorithms')
                .success(function(response) {
                    $scope.algorithms = response;
                })
                .error(function(response) {
                    $scope.algorithms = 0;
                });
        }
    });
    $stateProvider.state({
        name: 'algorithms.algorithm',
        url: '/{algorithm_id:[0-9]+}',
        templateUrl: 'html/algorithms/algorithm'
    });
});