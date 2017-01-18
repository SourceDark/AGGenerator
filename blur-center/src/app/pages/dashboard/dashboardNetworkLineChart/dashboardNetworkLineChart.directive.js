/**
 * Created by Nettle on 2017/1/18.
 */

(function () {
    'use strict';

    angular.module('BlurAdmin.pages.dashboard')
        .directive('dashboardNetworkLineChart', dashboardNetworkLineChart);

    /** @ngInject */
    function dashboardNetworkLineChart() {
        return {
            restrict: 'E',
            scope: {
                id   : '=networkId'
            },
            controller: 'DashboardNetworkLineChartCtrl',
            templateUrl: 'app/pages/dashboard/dashboardNetworkLineChart/dashboardNetworkLineChart.html'
        };
    }
})();
