/**
 * Created by Nettle on 2017/1/18.
 */

(function () {
    'use strict';

    angular.module('BlurAdmin.pages.dashboard')
        .controller('DashboardNetworkLineChartCtrl', DashboardNetworkLineChartCtrl);

    /** @ngInject */
    function DashboardNetworkLineChartCtrl($scope,$filter) {

        $scope.series = ["cvssMin","DepthMetric","weakestAdversary","connectivityMetric","cycleMetric","attackability","cvssAverage","cvssMax"];

        if ($scope.id == 1) {
            $scope.networkName = 'serc-1730';
            $scope.dataTime = [1484555250000,1484555303000,1484555491000,1484642965000];
            $scope.data = [
                [4.3,4.3,4.3,4.3],
                [9.4,9.4,9.4,9.4],
                [3.43,3.43,3.43,5.57],
                [10.0,10.0,10.0,10.0],
                [8.0,8.0,8.0,8.0],
                [0,0,0,4.1772151898734],
                [5.1,5.1,5.1,5.1],
                [7.5,7.5,7.5,7.5]
            ]
        }   else {
            $scope.networkName = 'beidasoft';
            $scope.dataTime = [1484642966000,1484555303000,1484555491000,1484555250000];
            $scope.data = [
                [3.5,3.5,3.5,3.5],
                [5.6,5.6,5.6,5.6],
                [5.57,3.35,3.35,3.35],
                [10.0,10.0,10.0,10.0],
                [5.6,5.6,5.6,5.6],
                [0,0,0,0],
                [4.6666665,4.6666665,4.6666665,4.6666665],
                [6.5,6.5,6.5,6.5]
            ]
        }
        $scope.labels = $scope.dataTime.map(function (t) {
            return new $filter('date')(t,'yyyy-MM-dd HH:mm:ss');
        });

    }

})();