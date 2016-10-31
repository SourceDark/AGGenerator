var aggeneratorApp = angular.module('aggeneratorApp', []);

aggeneratorApp.controller('indexController', function IndexController($scope, $http) {
    $scope.Status = {
        Init: 0,
        LightProbing: 1,
        DeepProbing: 2,
        LightProbed: 3,
        DeepProbed: 4
    };
    $scope.currentStatus = $scope.Status.Init;
    $scope.target = "192.168.100.233/24";
    $scope.lightProbe = function() {
        if ($scope.currentStatus == $scope.Status.LightProbing) return;
        if ($scope.currentStatus == $scope.Status.DeepProbing) return;
        $scope.currentStatus = $scope.Status.LightProbing;
        $http.get("api/lightProbe?ip=" + $scope.target)
            .success(function(response) {
                console.log(response);
                $scope.hosts = response['alive_hosts'];
                $scope.currentStatus = $scope.Status.LightProbed;
            });
    };
    $scope.deepProbe = function() {
        if ($scope.currentStatus == $scope.Status.LightProbing) return;
        if ($scope.currentStatus == $scope.Status.DeepProbing) return;
        $scope.currentStatus = $scope.Status.DeepProbing;
    };
});