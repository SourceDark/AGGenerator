agbotApp.config(function($stateProvider) {
    $stateProvider.state({
        name: 'tasks',
        url: '/tasks/{page}',
        templateUrl: 'html/tasks',
        controller: function ($scope, $http, $stateParams) {
            $scope.currentPage = $stateParams.page;
            $scope.status = 'loading';
            $scope.empty = 'No tasks exists.';
            $scope.tasks = null;
            $http.get('api/tasks?size=15&sort=id,desc&page=' + ($stateParams.page - 1))
                .success(function(response) {
                    $scope.tasks = response;
                    if ($scope.tasks.totalPages < 1) {
                        $scope.status = 'empty';
                    }
                    else {
                        $scope.status = 'list';
                        $scope.pages = [];
                        for(var i = -4; i < 5; i++) {
                            var page = $scope.tasks.number + i;
                            if(page >= 0 && page < $scope.tasks.totalPages ) {
                                $scope.pages.push(page + 1);
                            }
                        }
                    }
                })
                .error(function(response) {
                    $scope.status = 'fail';
                });
        }
    });
});