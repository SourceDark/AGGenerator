/**
 * Created by Nettle on 2017/1/18.
 */

(function () {
    'use strict';

    angular.module('BlurAdmin.pages.dashboard')
        .controller('DashboardSensorsChartCtrl', DashboardSensorsChartCtrl);

    /** @ngInject */
    function DashboardSensorsChartCtrl($scope,$filter,$http,$timeout, baConfig) {
        var layoutColors = baConfig.colors;
        $scope.options = {
          elements: {
            arc: {
              borderWidth: 0
            }
          },
          legend: {
            display: true,
            position: 'bottom',
            labels: {
              fontColor: layoutColors.defaultText
            }
          }
        };
        $scope.hosts = [];
        $scope.vulnerabilities = [];
        $scope.top10Hosts = [];
        $scope.top10Vulnerabilities = [];            
        $scope.hostVulnerabilityPieLabels = ['没有漏洞的主机', '存在漏洞的主机'];        
        $scope.hostVulnerabilityPieData = [1,1];
        $scope.vulnerabilityCvssPieLabels = ['普通漏洞', '高危漏洞', '中等漏洞']
        $scope.vulnerabilityCvssPieData = [1,1,1];
        $timeout(function () {
            $http.get('http://162.105.30.71:9016/networks/'+$scope.id+'/sensors').then(function (result) {
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
                $scope.hostVulnerabilityPieData[1] = $scope.hosts.filter(function(host) {return host.vulnerabilityCount > 0}).length;
                $scope.hostVulnerabilityPieData[0] = $scope.hosts.length - $scope.hostVulnerabilityPieData[0];
                $scope.vulnerabilityCvssPieData[1] = $scope.vulnerabilities.filter(function(v) {return v.cvssScore >= 7}).length;
                $scope.vulnerabilityCvssPieData[2] = $scope.vulnerabilities.filter(function(v) {return v.cvssScore < 7 && v.cvssScore >= 4}).length;
                $scope.vulnerabilityCvssPieData[0] = $scope.vulnerabilities.length - $scope.vulnerabilityCvssPieData[0] - $scope.vulnerabilityCvssPieData[1];
            }, function (result) {
                console.error('error');
            });
        }, 1000);
    }

})();