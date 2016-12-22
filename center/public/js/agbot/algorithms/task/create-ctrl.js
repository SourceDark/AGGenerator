agbotApp.config(function($stateProvider) {
    var algorithms = {
        '3': 'bayesianNetworkCtrl'
    };
    $stateProvider.state({
        name: 'algorithms.newAlgorithmTask',
        url: '/{algorithm_id:[0-9]+}/newTask',
        templateUrl: function ($routeParams) {
            if (algorithms[$routeParams.algorithm_id])
                return 'html/algorithms/algorithm_new_task/' + $routeParams.algorithm_id;
            return 'html/algorithms/algorithm_new_task/default';
        }
    });
});