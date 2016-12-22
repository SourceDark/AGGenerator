/**
 * Created by Nettle on 2016/12/21.
 */
agbotApp
    .controller('bayesianNetworkCtrl', ['$http', '$scope', '$q', '$stateParams', function ($http, $scope, $q, $stateParams) {
        $scope.task = {
            algorithm_id: null,
            result_id: null
        };

        $scope.resultCache = {};
        $scope.noResultFlag = true;

        $q
            .all([$http.get(['api','algorithms'].join('/')), $http.get(['api','algorithms', $stateParams.algorithm_id].join('/'))])
            .then(function (result) {
                $scope.algorithm = result[1].data;
                $scope.algorithms = result[0].data.filter(function (algo) {
                    return algo.outputType === $scope.algorithm.inputType && algo.id != $scope.algorithm.id;
                });
            });

        $scope.selectAlgorithm = function () {
            $scope.task.result_id = null;
            if (!$scope.resultCache[$scope.task.algorithm_id]) {
                $http
                    .get(['api', 'algorithms', $scope.task.algorithm_id, 'tasks'].join('/'))
                    .success(function (data) {
                        $scope.resultCache[$scope.task.algorithm_id] = data;
                        $scope.results = $scope.resultCache[$scope.task.algorithm_id];
                        if ($scope.results.length < 1)
                            $scope.noResultFlag = true;
                        else {
                            $scope.noResultFlag = false;
                            $scope.task.result_id = $scope.results[0].id;
                        }
                    });
            }   else {
                $scope.results = $scope.resultCache[$scope.task.algorithm_id];
                if ($scope.results.length < 1)
                    $scope.noResultFlag = true;
                else {
                    $scope.noResultFlag = false;
                    $scope.task.result_id = $scope.results[0].id;
                }
            }
        };

        $scope.submit = function () {
            console.log($scope.nodeSelected);
        }
    }])
    .directive('topologyEventGraph', ['$http', '$q', function ($http, $q) {
        return {
            restrict: 'E',
            replace: true,
            transclude: true,
            scope: {
                nodes: '=nodes',
                links: '=links',
                selected: '=nodeSelected'
            },
            template: '<svg id="topology_event_graph"></svg>',
            link: function (scope, element, attrs) {
                scope.selected = [1,0,1];
            }
        }
    }]);