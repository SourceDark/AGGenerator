angular.module('AGGenerator', ['ui.router'])
	.controller('mainCtrl', ['$scope', '$state', function($scope, $state) {
		$scope.nav = [
			{id: 'network_topology'  , text: '网络拓扑'},
			{id: 'network_config'    , text: '网络配置'},
			{id: 'researcher_analysis', text: '论文调研'},
		];
		$state.go('network_topology');
	}]);
