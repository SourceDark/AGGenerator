<div ng-loading="status" ng-loading-empty="empty">
    <table class="table">
        <thead>
        <tr>
            <td>ID</td>
            <td>Name</td>
            <td>Operation</td>
        </tr>
        </thead>
        <tbody>
        <tr ng-repeat="algorithm in algorithms | orderBy:algorithm.id">
            <td>{{algorithm.id}}</td>
            <td>{{algorithm.name}}</td>
            <td><a href="#" ng-click="export(algorithm.id)">Export</a></td>
        </tr>
        </tbody>
    </table>
</div>