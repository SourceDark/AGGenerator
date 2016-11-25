angular.module('agbotApp')
	.config(['$stateProvider', function($stateProvider) {
		$stateProvider.state('attack_graph', {
			url: '/attack_graph',
			templateUrl: '/html/attackGraph',
			controller: 'attackGraphCtrl'
		});
	}])
	.controller('attackGraphCtrl', ['$scope', function($scope) {
		$scope.graph = researcherAnalysisData;
		$scope.options = {
			distance: 300,
			charge  : -300
		};
	}]);