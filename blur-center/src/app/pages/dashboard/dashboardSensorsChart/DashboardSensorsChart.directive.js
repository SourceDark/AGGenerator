/**
 * Created by Nettle on 2017/1/18.
 */

(function () {
    'use strict';

    angular.module('BlurAdmin.pages.dashboard')
        .directive('dashboardSensorsChart', dashboardSensorsChart);

    /** @ngInject */
    function dashboardSensorsChart() {
        return {
            restrict: 'E',
            scope: {
                id   : '=networkId'
            },
            controller: 'DashboardSensorsChartCtrl',
            templateUrl: 'app/pages/dashboard/dashboardSensorsChart/DashboardSensorsChart.html'
        };
    }
})();
