/**
 * @author v.lugovksy
 * created on 16.12.2015
 */
(function () {
  'use strict';

  angular.module('BlurAdmin.pages.dashboard')
      .controller('DashboardPieChartCtrl', DashboardPieChartCtrl);

  /** @ngInject */
  function DashboardPieChartCtrl($scope, $timeout, baConfig, baUtil, $http, apiHost) {
    var pieColor = baUtil.hexToRGB(baConfig.colors.defaultText, 0.2);
    $scope.charts = [
      {
        color: pieColor,
        description: '探针数',
        stats: 0,
        icon: 'socicon',
        iconText: '_'
      }, {
        color: pieColor,
        description: '主机数',
        stats: 0,
        icon: 'fa fa-server',
      }, {
        color: pieColor,
        description: '高危漏洞数',
        stats: 0,
        icon: 'fa fa-bolt',
      }, {
        color: pieColor,
        description: '漏洞数',
        stats: 0,
        icon: 'fa fa-bug',
      }
    ];
    $http.get(apiHost + '/networks/'+$scope.id).then(function (result) {
          $scope.charts[0].stats = result.data.sensorCount;
          $scope.charts[1].stats = result.data.hostCount;
          $scope.charts[2].stats = result.data.dangerVulnerabilityCount;
          $scope.charts[3].stats = result.data.vulnerabilityCount;
      }, function (result) {
          console.error('error');
    });
    
    function loadPieCharts() {
      $('.chart').each(function () {
        var chart = $(this);
        chart.easyPieChart({
          easing: 'easeOutBounce',
          onStep: function (from, to, percent) {
            $(this.el).find('.percent').text(Math.round(percent));
          },
          barColor: chart.attr('rel'),
          trackColor: 'rgba(0,0,0,0)',
          size: 84,
          scaleLength: 0,
          animation: 2000,
          lineWidth: 9,
          lineCap: 'round',
        });
      });
    }

  }
})();