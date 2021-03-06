<div class="container">
    <p class="route-path"></p>
    <div class="board">
        <div ng-loading="status" ng-loading-empty="empty">
            <table class="table mid">
                <thead>
                <tr>
                    <td>#</td>
                    <td>Algorithm</td>
                    <td>Container</td>
                    <td>Content Type</td>
                    <td>Status</td>
                    <td>Updated At</td>
                    <td>Operation</td>
                </tr>
                </thead>
                <tbody>
                <tr ng-repeat="task in tasks.content">
                    <td><a ui-sref="task.info({taskId:task.id})">{{task.id}}</a></td>
                    <td><a ui-sref="algorithms.algorithm({algorithm_id:task.algorithm.id})">{{task.algorithm.name}}</a></td>
                    <td class="code">{{task.containerId.substr(0, 10)}}</td>
                    <td>{{task.inputType}} => {{task.outputType}}</td>
                    <td ng-if="task.status=='running'"><a class="running" ui-sref="task.info({taskId:task.id})">Running</a></td>
                    <td ng-if="task.status=='created'"><a class="created" ui-sref="task.info({taskId:task.id})">Created</a></td>
                    <td ng-if="task.status=='success'"><a class="success" ui-sref="task.info({taskId:task.id})">Success</a></td>
                    <td ng-if="task.status=='failure'"><a class="fail" ui-sref="task.info({taskId:task.id})">Failure</a></td>
                    <td ng-bind="task.updatedTime | date:'yyyy-MM-dd HH:mm:ss'"></td>
                    <td><a ng-if="task.outputType=='attack_graph2.0' || task.outputType=='attack_graph'" href="#/algorithms/{{task.algorithm.id}}/attack_graph/{{task.id}}" target="_blank"><i class="fa fa-eye" aria-hidden="true"></i></a></td>
                </tr>
                </tbody>
            </table>
        </div>
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <li ng-class="{disabled: tasks.first}">
                    <a class="clickable" ng-click="loadPage(1)" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                <li ng-repeat="page in pages" ng-class="{active: page == currentPage}">
                    <a class="clickable" ng-click="loadPage(page)">{{page}}</a>
                </li>
                <li ng-class="{disabled: tasks.last}">
                    <a class="clickable" ng-click="loadPage(tasks.totalPages)" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </ul>
        </nav>
    </div>
</div>