<div class="container">
    <p class="route-path"></p>
    <div class="board">
        <a ui-sref="algorithms.new">
            <button type="button" class="btn btn-default">
                <span class="glyphicon glyphicon-plus"></span> New Algorithm
            </button>
        </a>
        <div>
            <div class="display info" ng-if="algorithms == null"><i class="fa fa-spinner fa-spin" aria-hidden="true"></i> Loading.</div>
            <div class="display error" ng-if="algorithms == 0"><i class="fa fa-warning" aria-hidden="true"></i> Can't connect to server.</div>
            <div class="display info" ng-if="algorithms != null && algorithms != 0 && algorithms.length == 0">No algorithm exists.</div>
            <table class="table" ng-if="algorithms != null  && algorithms != 0 && algorithms.length > 0">
                <thead>
                <tr>
                    <td>#</td>
                    <td>Name</td>
                    <td>Image</td>
                    <td>Input Type</td>
                    <td>Output Type</td>
                    <td>Updated At</td>
                </tr>
                </thead>
                <tbody>
                    <tr ng-repeat="algorithm in algorithms">
                        <td>{{algorithm.id}}</td>
                        <td><a ui-sref="algorithms.algorithm({algorithm_id:algorithm.id})">{{algorithm.name}}</a></td>
                        <td>{{algorithm.image}}</td>
                        <td>{{algorithm.inputType}}</td>
                        <td>{{algorithm.outputType}}</td>
                        <td ng-bind="algorithm.updatedTime | date:'yyyy-MM-dd HH:mm:ss'"></td>
                        <td style="vertical-align: middle;"><a ui-sref="algorithms.edit({algorithm_id:algorithm.id})" title="config"><span class="glyphicon glyphicon-cog"></span></a></td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>