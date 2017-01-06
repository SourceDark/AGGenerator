<!-- Algorithm -->
<p>
    <span class="key">Algorithm: </span>
    <span>
        <a ui-sref="algorithms.algorithm({algorithm_id:task.algorithm.id})">{{task.algorithm.name}}</a>
    </span>
</p>
<!-- Status -->
<p>
    <span class="key">Status: </span>
    <span ng-if="task.status=='success'" class="success">Success</span>
    <span ng-if="task.status=='failure'" class="failure">Failure</span>
    <span ng-if="task.status=='created'" class="created">Created</span>
    <span ng-if="task.status=='running'" class="running">Running</span>
</p>
<!-- Uploaded at -->
<p>
    <span class="key">Uploaded at: </span>
    <span ng-bind="task.updatedTime | date:'yyyy-MM-dd HH:mm:ss'"></span>
</p>
<!-- Input -->
<div ng-if="input != null">
    <p>
        <span class="key">Input: </span>
    </p>
    <div class="json-container" ng-if="isInputJson">
        <json-formatter json="input" open="1"></json-formatter>
    </div>
    <pre class="json-container" ng-if="!isInputJson">{{input}}</pre>
</div>
<!-- Output -->
<div ng-if="output != null">
    <p>
        <span class="key">Output: </span>
    </p>
    <div class="json-container" ng-if="isOutputJson">
        <json-formatter json="output" open="1"></json-formatter>
    </div>
    <pre class="json-container" ng-if="!isOutputJson">{{output}}</pre>
</div>
<!-- ErrorStack -->
<div ng-if="errorStack != null">
    <p>
        <span class="key">ErrorStack: </span>
    </p>
    <div class="json-container" ng-if="isErrorStackJson">
        <json-formatter json="errorStack" open="1"></json-formatter>
    </div>
    <pre class="json-container" ng-if="!isErrorStackJson">{{errorStack}}</pre>
</div>
