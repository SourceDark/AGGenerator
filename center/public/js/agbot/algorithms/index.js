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
            $scope.status = 'loading';
            $scope.empty = 'No algorithm exists.';
            $scope.algorithms = null;
            $http.get('api/algorithms')
                .success(function(response) {
                    $scope.algorithms = response;
                    if ($scope.algorithms.length < 1)
                        $scope.status = 'empty';
                    else $scope.status = 'list';
                })
                .error(function(response) {
                    $scope.status = 'fail';
                });
        }
    });
    $stateProvider.state({
        name: 'algorithms.algorithm',
        url: '/{algorithm_id:[0-9]+}',
        templateUrl: 'html/algorithms/algorithm'
    });
});