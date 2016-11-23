agbotApp.config(function($stateProvider) {
    $stateProvider.state({
        name: 'algorithms',
        url: '/algorithms',
        template: '<ui-view></ui-view>',
        controller: function($state) {
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
        url: '/{id:[0-9]+}',
        templateUrl: 'html/algorithms/algorithm'
    });
});