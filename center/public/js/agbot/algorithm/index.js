/**
 * Routers
 */
agbotApp.config(function($stateProvider) {
    $stateProvider.state({
        name: 'algorithms',
        url: '/algorithms',
        templateUrl: 'html/algorithms'
    });
    $stateProvider.state({
        name: 'algorithm',
        url: '/algorithms/{id:[0-9]+}',
        templateUrl: 'html/algorithm'
    });
});