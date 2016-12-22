/**
 * Created by Nettle on 2016/12/21.
 */

agbotApp
    .controller('defaultCtrl', ['$scope', '$http', '$stateParams', '$state', function ($scope, $http, $stateParams, $state) {
        $scope.flag = 'default';
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
            $http.post("/api/algorithms/" + $stateParams.algorithm_id + "/tasks/generation", $scope.task)
            .success(function() {
                $state.go('algorithms.algorithm', {algorithm_id: $stateParams.algorithm_id});
            });
        }
    }]);