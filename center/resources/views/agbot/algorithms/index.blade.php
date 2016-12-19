<div class="container">
    <p class="route-path"></p>
    <div class="board">
        <table class="table">
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
            </tr>
            </tbody>
        </table>
    </div>
</div>