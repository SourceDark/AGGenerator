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
                    <td>Status</td>
                    <td>Updated At</td>
                </tr>
                </thead>
                <tbody>
                <tr ng-repeat="task in tasks.content">
                    <td>{{task.id}}</td>
                    <td><a ui-sref="algorithms.algorithm({algorithm_id:task.algorithm.id})">{{task.algorithm.name}}</a></td>
                    <td class="code">{{task.containerId}}</td>
                    <td ng-if="task.status=='running'"><a class="running">Running</a></td>
                    <td ng-if="task.status=='created'"><a class="created">Created</a></td>
                    <td ng-if="task.status=='success'"><a class="success" href="#/algorithms/{{task.algorithm_id}}/results/{{task.result_id}}/info">Success</a></td>
                    <td ng-if="task.status=='failure'"><a class="fail">Failure</a></td>
                    <td ng-bind="task.updatedTime | date:'yyyy-MM-dd HH:mm:ss'"></td>
                    <td style="vertical-align: middle;"><a ui-sref="algorithms.edit({algorithm_id:algorithm.id})" title="config"><span class="glyphicon glyphicon-cog"></span></a></td>
                </tr>
                </tbody>
            </table>
            <nav aria-label="Page navigation">
                <ul class="pagination">
                    <li ng-class="{disabled: tasks.first}">
                        <a ui-sref="tasks({page:1})" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                    <li ng-repeat="page in pages" ng-class="{active: page == currentPage}">
                        <a ui-sref="tasks({page:page})" ng-bind="page">1</a>
                    </li>
                    <li ng-class="{disabled: tasks.last}">
                        <a ui-sref="tasks({page:tasks.totalPages})" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</div>