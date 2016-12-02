<div ng-if="result == null || !result.export_tasks.length">
    <p>No task has been exported from this result . <a href="#" ui-sref="algorithms.result.newTask">Create one ?</a></p>
</div>
<div ng-if="result != null && result.export_tasks.length">
    <a href="#" ui-sref="algorithms.result.newTask">Create new task</a>
    <table class="table">
        <thead>
            <tr>
                <td>#</td>
                <td>Algorithm ID</td>
                <td>Result ID</td>
                <td>Container ID</td>
                <td>Status</td>
            </tr>
        </thead>
        <tbody>
            <tr ng-repeat="task in result.export_tasks | orderBy:task.id:'desc'">
                <td>{{task.id}}</td>
                <td>{{task.algorithm_id}}</td>
                <td ng-if="task.result_id">{{task.result_id}}</td>
                <td ng-if="!task.result_id">/</td>
                <td>{{task.container_id}}</td>
                <td ng-if="task.status==0"><a class="running">Running</a></td>
                <td ng-if="task.status==1"><a class="success" href="#/algorithms/{{task.algorithm_id}}/results/{{task.result_id}}/info">Success</a></td>
                <td ng-if="task.status==2"><a class="fail">Fail</a></td>
            </tr>
        </tbody>
    </table>

</div>
