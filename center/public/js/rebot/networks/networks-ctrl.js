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
                $scope.networks = [
                    {
                        id: 1,
                        name: 'serc-1730',
                        sensors: 2,
                        hosts: 11,
                        vulnerabilities: 13,
                        score: 7.6
                    },
                    {
                        id: 2,
                        name: 'beidasoft',
                        sensors: 3,
                        hosts: 114,
                        vulnerabilities: 18,
                        score: 9.5
                    }
                ];
            };
            $scope.loadNetworks();
        }
    });
});