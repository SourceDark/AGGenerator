/**
 * @author v.lugovksy
 * created on 16.12.2015
 */
(function () {
  'use strict';

  angular.module('BlurAdmin.pages.dashboard')
      .controller('DashboardPieChartCtrl', DashboardPieChartCtrl);

  /** @ngInject */
  function DashboardPieChartCtrl($scope, $timeout, baConfig, baUtil, $http) {
    var pieColor = baUtil.hexToRGB(baConfig.colors.defaultText, 0.2);
    $scope.charts = [
      {
        color: pieColor,
        description: '探针数',
        stats: 0,
        icon: 'socicon',
        iconText: '_',
          info: '部署在您网络中用以探查网络状况的探针数量'
      }, {
        color: pieColor,
        description: '主机数',
        stats: 0,
        icon: 'fa fa-server',
            info: '您网络中被探查到的主机数量'
      }, {
        color: pieColor,
        description: '高危漏洞数',
        stats: 0,
        icon: 'fa fa-bolt',
            info: '可能对您网络安全产生影响的漏洞数量'
      }, {
        color: pieColor,
        description: '漏洞数',
        stats: 0,
        icon: 'fa fa-bug',
            info: '探针扫描到的总漏洞数量'
      }, {
        color: pieColor,
        description: '高危主机数',
        stats: 0,
        icon: 'fa fa-warning',
            info: '您网络中被高危漏洞感染的主机数量'
      }, {
        color: pieColor,
        description: '总风险值',
        stats: 0,
        icon: 'fa fa-exclamation-circle',
            info: '您网络目前的综合健康分数'
      }
    ];
    $http.get('http://162.105.30.200:9016/server/'+$scope.id+'/overview').then(function (result) {
          $scope.charts[0].stats = result.data.sensorCount;
          $scope.charts[1].stats = result.data.hostCount;
          $scope.charts[2].stats = result.data.dangerVulnerabilityCount;
          $scope.charts[3].stats = result.data.vulnerabilityCount;
          $scope.charts[4].stats = result.data.dangerHostCount;
          $scope.charts[5].stats = result.data.score;
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