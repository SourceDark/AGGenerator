/**
 * Created by Nettle on 2016/12/20.
 */
agbotApp.config(['$stateProvider', function($stateProvider) {
    $stateProvider.state('algorithms.edit', {
        url: '/{algorithm_id:[0-9]+}/edit',
        templateUrl: '/html/algorithms/algorithm_edit',
        controller: 'algorithmEditCtrl'
    }).state('algorithms.new', {
        url: '/new',
        templateUrl: '/html/algorithms/algorithm_edit',
        controller: 'algorithmEditCtrl'
    });
}]).controller('algorithmEditCtrl', ['$scope', '$stateParams', '$http', '$q', function($scope, $stateParams, $http, $q) {
    // console.log($stateParams.id);
    $scope.algorithm_id = $stateParams.algorithm_id;
    $scope.createResultType = false;

    if ($scope.algorithm_id) {
        $http
            // api/algorithm?uri=/algorithms
            .get(['api','algorithm?uri=','algorithms',$scope.algorithm_id].join('/'))
            .then(function (result) {
                $scope.algorithm = result.data;
            });
    }   else {
        $scope.algorithm = {
            name: '',
            image: '',
            inputType: '',
            outputType: ''
        };
    }

    $http
        .get(['api','algorithm?uri=','result-types'].join('/'))
        .then(function (result) {
            $scope.resultTypes = result.data;
        });

    $scope.newDataType = function (type) {
        $scope.dataType = {
            name: '',
            description: ''
        };
        $scope.createResultType = true;
    };

    $scope.submitDataType = function () {
        if ($scope.dataType.name.length > 0) {
            if ($scope.resultTypes
                    .filter(function (type) {
                        return type.name == $scope.dataType.name
                    }).length > 0)   {
                alert('"Name" has already existed');
                return ;
            }
            console.log($scope.dataType);
            $http
                .post(['api','algorithm?uri=','result-types'].join('/'), $scope.dataType)
                .then(function (result) {
                    $scope.resultTypes.push($scope.dataType);
                    $scope.createResultType = false;
                });
        }   else {
            alert('"Name" should not be empty!');
        }
    };

    $scope.cancelDataType = function () {
        if (!confirm('确定要取消新建类型？'))
            return ;
        $scope.createResultType = false;
    };

    $scope.submit = function () {
        console.log($scope.algorithm);
    };
}]);