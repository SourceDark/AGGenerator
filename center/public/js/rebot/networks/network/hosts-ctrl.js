agbotApp.config(function($stateProvider) {
    $stateProvider.state({
        name: 'network.hosts',
        url: '/hosts',
        templateUrl: 'html/networks/network/hosts',
        controller: function ($scope, $http, $stateParams) {
            $scope.sensorVulnerabilitiesCount = function($sensor) {
                var count = 0;
                for (var i = 0; i < $sensor['hosts'].length; i++) {
                    count += $scope.hostVulnerabilitiesCount($sensor['hosts'][i]);
                }
                return count;
            };
            $scope.hostVulnerabilitiesCount = function($host) {
                var count = 0;
                for (var i = 0; i < $host['vulnerabilities'].length; i++) {
                    count += $host['vulnerabilities'][i]['cveList'].length;
                }
                return count;
            }
        }
    });
});