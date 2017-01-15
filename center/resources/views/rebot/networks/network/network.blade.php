<div class="container">
    <p class="route-path"><a class="item" ui-sref="networks">Networks</a><span> / </span><span class="item">{{network.name}}</span></p>
    <div class="board">
        <div class="tag-list">
            <div class="tag" ui-sref="network.info" ui-sref-active="active">Info</div>
            <div class="tag" ui-sref="network.hosts" ui-sref-active="active">Hosts</div>
        </div>
        <ui-view>

        </ui-view>
    </div>
</div>