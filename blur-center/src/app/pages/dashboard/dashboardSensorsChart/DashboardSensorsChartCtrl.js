/**
 * Created by Nettle on 2017/1/18.
 */

(function () {
    'use strict';

    angular.module('BlurAdmin.pages.dashboard')
        .controller('DashboardSensorsChartCtrl', DashboardSensorsChartCtrl);

    /** @ngInject */
    function DashboardSensorsChartCtrl($scope,$filter,$http,$timeout) {
        function mySort(a, b) {
            return a.t > b.t;
        }
        $timeout(function () {
            $scope.top10Hosts = [];
            $scope.top10Vulnerabilities = [];
            $scope.hosts = [];
            $scope.vulnerabilities = [];
            $http
                .get('http://162.105.30.71:9016/networks/'+$scope.id+'/sensors')
                .then(function (result) {
                    $scope.sensors = result.data;
                    $scope.sensors.forEach(function(sensor) {
                        sensor.hosts.forEach(function(host){
                            host.sensor = sensor;
                            host.vulnerabilityCount = 0;
                            host.cvssScoreTotoal = 0;
                            host.maxCvssScore = 0;
                            host.mostDangerVulnerability;
                            $scope.hosts.push(host);
                            host.vulnerabilities.forEach(function(vulnerability) {
                                vulnerability.cveList.forEach(function(cve) {
                                    if(cve) {
                                        host.vulnerabilityCount++;
                                        host.cvssScoreTotoal += cve.cvssScore;
                                        if(cve.cvssScore > host.maxCvssScore) {
                                            host.maxCvssScore = cve.cvssScore;
                                            host.mostDangerVulnerability = cve;
                                        }
                                        cve.host = host;
                                        $scope.vulnerabilities.push(cve);
                                    }                                    
                                });
                            });
                        })
                    });
                    $scope.top10Vulnerabilities = $scope.vulnerabilities.sort(function(a, b){return b.cvssScore - a.cvssScore}).slice(0,10);
                    $scope.top10Hosts = $scope.hosts.sort(function(a, b){return b.cvssScoreTotoal - a.cvssScoreTotoal}).slice(0,10);
                }, function (result) {
                    console.error('error');
                });
        }, 1000);
    }

})();