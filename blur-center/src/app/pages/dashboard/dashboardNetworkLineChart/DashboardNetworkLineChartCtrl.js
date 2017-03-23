/**
 * Created by Nettle on 2017/1/18.
 */

(function () {
    'use strict';

    angular.module('BlurAdmin.pages.dashboard')
        .controller('DashboardNetworkLineChartCtrl', DashboardNetworkLineChartCtrl);

    /** @ngInject */
    function DashboardNetworkLineChartCtrl($scope,$filter,$http,$timeout, apiHost) {
        function mySort(a, b) {
            return a.t > b.t;
        }
        $timeout(function () {
            $http
                .get('http://162.105.30.200:9016/networks/'+$scope.id+'/scores')
                .then(function (result) {
                    console.log(result);
                    $scope.lineData = [];
                    for (var key in result.data) {
                        var data = {};
                        data.y = new $filter('date')(parseInt(key),'yyyy-MM-dd HH:mm:ss');
                        data.t = parseInt(key);
                        data['攻击可能性分数'] = result.data[key]['attack-likehood'];
                        data['攻击性分数'] = result.data[key]['attackability'];
                        data['K零日分数'] = result.data[key]['k-zero'];
                        data['CVSS分数'] = result.data[key]['cvssAverage'];
                        $scope.lineData.push(data);
                    }
                    $scope.lineData.sort(mySort);
                    $scope.series = [];
                    $scope.radarData = [];
                    for (var key in $scope.lineData[0]){
                        if (key != 'y' && key != 't') {
                            $scope.series.push(key);
                            $scope.radarData.push(parseFloat($scope.lineData[$scope.lineData.length - 1][key]));
                        }
                    }
                    $scope.radarData = [$scope.radarData];
                }, function (result) {
                    console.error('error');
                });
        }, 1000);
    }

})();