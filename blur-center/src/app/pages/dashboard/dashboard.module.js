/**
 * @author v.lugovsky
 * created on 16.12.2015
 */
(function () {
  'use strict';

  angular.module('BlurAdmin.pages.dashboard', [])
      .config(routeConfig)
      .controller('dashboardCtrl', function($scope,$state) {
          $scope.networkId = 1;
      });

  /** @ngInject */
  function routeConfig($stateProvider) {
    $stateProvider
        .state('dashboard', {
          url: '/dashboard',
          templateUrl: 'app/pages/dashboard/dashboard.html',
          title: '总览',
          sidebarMeta: {
            icon: 'ion-android-home',
            order: 0
          }
        })
  }

})();
