agbotApp.config(function($stateProvider) {
    $stateProvider.state({
        name: 'network',
        url: '/network/{networkId:[0-9]+}',
        templateUrl: 'html/networks/network',
        controller: function ($scope, $http, $stateParams, $state) {
            $scope.status = 'loading';
            $scope.empty = 'No sensor exists.';

            $scope.loadNetwork = function (networkId) {
                $scope.status = 'list';
                $http.get('api/networks/' + networkId)
                    .success(function(response) {
                        $scope.network = response;
                    })
                    .error(function(response) {
                    });
                $http.get('api/networks/' + networkId + '/sensors')
                    .success(function(response) {
                        $scope.sensors = response;
                        if ($scope.sensors.length < 1)
                            $scope.status = 'empty';
                        else $scope.status = 'list';
                    })
                    .error(function(response) {
                        $scope.status = 'fail';
                    });
            };
            $scope.loadNetwork($stateParams['networkId']);

            if ($state.current.name == "network") {
                $state.go("network.info");
            }
        }
    });
});