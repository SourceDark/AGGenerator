<div ng-controller="algorithmCtrl">
    <div class="container">
        <h3>{{algorithm.name}} <small><a href='#' ui-sref="algorithms.index">return to list</a></small></h3>
        <h3>results</h3>
        <ul>
            <li ng-repeat="result in results">{{result.id}} {{result.created_at}}</li>
        </ul>
    </div>
</div>