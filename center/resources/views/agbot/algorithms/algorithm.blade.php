<div ng-controller="algorithmCtrl">
    <div class="container">
        <h3>{{algorithm.name}} <small><a href='#' ui-sref="algorithms.index">return to list</a></small></h3>
        <h3>Results<small><a ui-sref="algorithms.newAlgorithmTask({algorithm_id: algorithm_id})">create new task</a></small></h3>
        <table class="table">
            <thead>
                <tr>
                    <td>Global ID</td>
                    <td>Finished time</td>
                    <td>Operation</td>
                </tr>
            </thead>
            <tbody>
                <tr ng-repeat="result in results">
                    <td>{{result.id}}</td>
                    <td>{{result.created_at}}</td>
                    <td>
                        <a href="#/algorithms/{{algorithm_id}}/attack_graph/{{result.id}}" target="_blank">Visualization</a>
                        <span ng-if="algorithm.type == 0">
                            / <a href="#/algorithms/{{algorithm_id}}/results/{{result.id}}">Analysis</a>
                        </span>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</div>