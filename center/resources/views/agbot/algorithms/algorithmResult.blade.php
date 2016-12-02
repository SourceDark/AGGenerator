<div>
    <div class="container">
        <p class="route-path"><a class="item" ui-sref="algorithms.index">Algorithms</a><span> / </span><a class="item" href="#/algorithms/{{algorithm.id}}">{{algorithm.name}}</a><span> / </span><span class="item">result #{{result_id}}</span></p>
        <p><span class="key">Uploaded at: </span>{{result.created_at}}</p>
        <p><span class="key">Content: </span><a href="#/algorithms/{{algorithm_id}}/attack_graph/{{result.id}}" target="_blank">Visualization</a></p>
        <div class="json-container">
            <jv-json-viewer data="result.content" editor="false"></jv-json-viewer>
        </div>
    </div>
</div>