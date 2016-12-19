agbotApp.config(function($stateProvider) {
    $stateProvider.state({
        name: 'algorithms.newAlgorithmTask',
        url: '/{algorithm_id:[0-9]+}/newTask',
        templateUrl: 'html/algorithms/tasks/new',
        controller: function ($scope, $http, $stateParams, $state) {
            $scope.task = {
                hacls: ""
            };
            $http.get("/api/sensors").success(function(sensors) {
                $scope.sensors = sensors;
            });

            $scope.submit = function() {
                $scope.task.sensors = $scope.sensors.filter(function(item) { 
                    return item.checked; 
                }).map(function(item) {
                    return item.name;
                });
                $http.post("/api/algorithm?uri=/algorithms/" + $stateParams.algorithm_id + "/tasks/generation", $scope.task)
                .success(function() {
                    $state.go('algorithms.algorithm', {algorithm_id: $stateParams.algorithm_id});
                });
            }
        }
    });
});