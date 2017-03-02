/**
 * @author v.lugovsky
 * created on 16.12.2015
 */
(function () {
  'use strict';

  angular.module('BlurAdmin.pages.dashboard', [])
      .config(routeConfig)
      .constant('apiHost', 'http://162.105.30.200:9016')
      .controller('dashboardCtrl', function($scope,$state) {
        if($state.current.name === 'serc1730') {
          $scope.networkId = 1;
        } else {
          $scope.networkId = 2;
        }
      });

  /** @ngInject */
  function routeConfig($stateProvider) {
    $stateProvider
        .state('serc1730', {
          url: '/serc1730',
          templateUrl: 'app/pages/dashboard/dashboard.html',
          title: '实验室',
          sidebarMeta: {
            icon: 'ion-android-home',
            order: 0,
          },
        }).state('beidasoft', {
          url: '/beidasoft',
          templateUrl: 'app/pages/dashboard/dashboard.html',
          title: '北大软件',
          sidebarMeta: {
            icon: 'ion-android-home',
            order: 0,
          },
        });
  }

})();
