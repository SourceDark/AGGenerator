<div class="container">
    <p class="route-path"><a class="item" ui-sref="tasks">Tasks</a><span> / </span><span class="item">#{{taskId}}</span></p>
    <div class="board">

        <div class="tag-list">
            <div class="tag" ui-sref="task.info" ui-sref-active="active">Info</div>
            <div class="tag" ui-sref="task.export" ui-sref-active="active">Export</div>
        </div>
        <ui-view>

        </ui-view>
    </div>
</div>