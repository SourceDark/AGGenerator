angular.module('AGGenerator')
	.config(['$stateProvider', function($stateProvider) {
		$stateProvider.state('network_config', {
			url: '/network_config',
			templateUrl: 'network-config.html',
			controller: 'networkConfigCtrl'
		});
	}])
	.controller('networkConfigCtrl', ['$scope', function($scope) {
		
		// center 1
		// senser 2
		// switch 3
		// host 4

		$scope.nodes = [];
		$scope.links = [];
		
		var typeList = {
			'center': {group: 1, size: 50},
			'Center': {group: 1, size: 50},
			'sensor': {group: 2, size: 30},
			'Sensor': {group: 2, size: 30},
			'switch': {group: 3, size: 40},
			'Switch': {group: 3, size: 40},
			'host': {group: 4, size: 20},
			'Host': {group: 4, size: 20},
		};
		
		if (window.localStorage) {
			$scope.info = localStorage.getItem('graph_info');
			if ($scope.info) {
				$scope.config = $scope.info;
				dealInfo($scope.config);
			}
		}
		
		$scope.apply = function() {
			$scope.info = $scope.config;
			dealInfo($scope.info);
		}
		
		$scope.save = function() {
			localStorage.setItem('graph_info', $scope.info);
		}
		
		
		function dealInfo(str) {
			var arr = str.split('\n');
			$scope.nodes = [];
			$scope.links = [];
			for (var i in arr) 
				if (arr[i][0] == '+') {
					var items = arr[i].substring(1, arr[0].length).split(',');
					if (items.length >= 2) {
						var node = {
							id: items[0],
							group: typeList[ items[1] ] ? typeList[ items[1] ].group : 5,
							size: typeList[ items[1] ] ? typeList[ items[1] ].size : 5,
							ips: []
						};
						for (var i = 2; i < items.length; ++i)
							node.ips.push(items[i]);						
						$scope.nodes.push(node);
					}
				}	else if (arr[i][0] == '-') {
					var items = arr[i].substring(1, arr[0].length).split(',');
					if (items.length >= 2) {
						var edge = {
							source: items[0],
							target: items[1],
							value : 1
						};
						$scope.links.push(edge);
					}
				}
		}
		
		
		$scope.options = {
			distance: 200,
			charge  : -500
		};
	}]);