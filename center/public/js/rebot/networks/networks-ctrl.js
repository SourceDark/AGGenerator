agbotApp.config(function($stateProvider) {
    $stateProvider.state({
        name: 'networks',
        url: '/networks',
        templateUrl: 'html/networks',
        controller: function ($scope, $http, $stateParams) {
            $scope.status = 'loading';
            $scope.empty = 'No network exists.';

            $scope.loadNetworks = function($page) {
                $scope.status = 'list';
                $http.get('api/networks')
                    .success(function(response) {
                        $scope.networks = response;
                        if ($scope.networks.length < 1)
                            $scope.status = 'empty';
                        else $scope.status = 'list';
                    })
                    .error(function(response) {
                        $scope.status = 'fail';
                    });
            };
            $scope.loadNetworks();

            $scope.average = function($scores) {
                console.log($scores);
                var sum = 0.0;
                sum += parseFloat($scores['weakestAdversary']);
                console.log(sum);
                sum += 10.0 / (1 + Math.log(1 + parseFloat($scores['attack_path_count'])));
                return sum / 2;
            }
        }
    });
});