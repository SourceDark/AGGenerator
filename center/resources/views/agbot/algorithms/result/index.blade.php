<div class="container">
    <p class="route-path"><a class="item" ui-sref="algorithms.index">Algorithms</a><span> / </span><a class="item" href="#/algorithms/{{algorithm.id}}">{{algorithm.name}}</a><span> / </span><span class="item">result #{{result_id}}</span></p>
    <div class="board">
        <div class="tag-list">
            <div class="tag" ui-sref="algorithms.result.info" ui-sref-active="active">Info</div>
            <div class="tag" ng-if="algorithm.type == 0" ui-sref="algorithms.result.tasks" ui-sref-active="active">Tasks</div>
            <div class="tag hide" ui-sref="algorithms.result.newTask" ui-sref-active="active">New</div>
        </div>
        <ui-view>

        </ui-view>
    </div>
</div>