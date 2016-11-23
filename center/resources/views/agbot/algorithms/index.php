<div ng-controller="algorithmsCtrl">
    <div class="container">
        <h3>Attack Graph Generation Algorithms</h3>
        <ul>
            <li ng-repeat="algorithm in algorithms.generation"><a ui-sref="algorithms.algorithm({id:algorithm.id})" >{{algorithm.name}}</a></li>
        </ul>
        <h3>Attack Graph Analysis Algorithms</h3>
        <ul>
            <li ng-repeat="algorithm in algorithms.analysis"><a ui-sref="algorithms.algorithm({id:algorithm.id})" >{{algorithm.name}}</a></li>
        </ul>
    </div>
</div>