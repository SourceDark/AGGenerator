<div ng-if="algorithms == null">
    <p>No algorithm available for this result .</p>
</div>
<div ng-if="algorithms != null">
    <p>List of available algorithms:</p>
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
