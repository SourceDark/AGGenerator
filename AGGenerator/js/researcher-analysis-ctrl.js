angular.module('AGGenerator')
	.config(['$stateProvider', function($stateProvider) {
		$stateProvider.state('researcher_analysis', {
			url: '/researcher_analysis',
			templateUrl: 'researcher-analysis.html',
			controller: 'researcherAnalysisCtrl'
		});
	}])
	.controller('researcherAnalysisCtrl', ['$scope', function($scope) {
		$scope.graph = researcherAnalysisData;
		$scope.options = {
			distance: 300,
			charge  : -300
		};
	}]);