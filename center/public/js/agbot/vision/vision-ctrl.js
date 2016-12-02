angular.module('agbotApp')
	.config(['$stateProvider', function($stateProvider) {
		$stateProvider.state('vision', {
			url: '/vision',
			templateUrl: '/html/vision',
			controller: 'visionCtrl'
		});
	}])
	.controller('visionCtrl', ['$scope', '$element', '$http', function($scope, $element, $http) {
		$scope.graph = {
			nodes: [
				{'id': 'PKU School Network', 'type': 'cloud', 'size': 50, 'ips': []},
				{'id': 'Switch 1', 'type': 'switch', 'size': 40, 'ips': []},
				{'id': 'xr-test', 'type': 'sensor', 'size': 40, 'ips': ['192.168.100.9', '162.105.30.73']}
			],
			links: [
				{'source': 'PKU School Network', 'target': 'Switch 1', value: 1},
				{'source': 'Switch 1', 'target': 'xr-test', value: 1}
			]
		};
		
		$scope.options = {
			distance: 200,
			charge  : -1000
		};

		function getNode(id) {
			for(var i = 0; i < $scope.graph.nodes.length; i++) {
				if($scope.graph.nodes[i].id.trim() == id.trim()) {
					return $scope.graph.nodes[i];
				}
			}
			return;
		}
		

		$http.get("/api/sensors").success(function(sensors) {
			sensors.forEach(function(sensor) {
				var sensorNode = getNode(sensor['name']);
				if(!sensorNode) {
					sensorNode = {'id': sensor['name'], 'type': 'sensor', 'size': 20, 'ips': [sensor['ip']]};
					$scope.graph.nodes.push(sensorNode);
				} 
				sensor.hosts.forEach(function(host) {
					host['nodeId'] = sensor['name'] + '-' + host['host_ip'];
					$scope.graph.nodes.push({'id': host['nodeId'], 'type': 'host', 'size': 20, 'reports': host.reports});
					$scope.graph.links.push({'source': sensor['name'], 'target': host['nodeId'], value: 1});
				});
				

			});
			$scope.ready = true;
		});
		
	}]);