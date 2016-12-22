<div ng-controller="algorithmCtrl">
    <div class="container">
        <h3>{{algorithm.name}} <small><a href='#' ui-sref="algorithms.index">return to list</a></small></h3>
        <h3>Results<small><a ui-sref="algorithms.newAlgorithmTask({algorithm_id: algorithm_id})"> create new task</a></small></h3>
        <table class="table">
            <thead>
                <tr>
                    <td>Global ID</td>
                    <td>Finished time</td>
                    <td>Status</td>
                    <td>Operation</td>
                </tr>
            </thead>
            <tbody>
                <tr ng-repeat="task in tasks">
                    <td><span ng-bind="task.id"></span></td>
                    <td><span ng-bind="task.createdTime | date:'yyyy-MM-dd HH:mm:ss'"></span></td>
                    <td ng-if="task.status=='running'"><a class="running">Running</a></td>
                    <td ng-if="task.status=='success'"><a class="success">Success</a></td>
                    <td ng-if="task.status=='failure'"><a class="fail">Fail</a></td>
                    <td ng-if="task.status=='created'"><a class="fail">created</a></td>
                    <td>
                        <a href="#/algorithms/{{algorithm_id}}/attack_graph/{{task.id}}" target="_blank">Visualization</a>
                        <a href="#/algorithms/{{algorithm_id}}/tasks/{{task.id}}">Analysis</a>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</div>