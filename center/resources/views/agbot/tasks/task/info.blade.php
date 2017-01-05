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
    <div class="json-container" ng-if="!isInputJson">
        <p>{{input}}</p>
    </div>
</div>
<!-- Output -->
<div ng-if="output != null">
    <p>
        <span class="key">Output: </span>
    </p>
    <div class="json-container" ng-if="isOutputJson">
        <json-formatter json="output" open="1"></json-formatter>
    </div>
    <div class="json-container" ng-if="!isOutputJson">
        <p>{{output}}</p>
    </div>
</div>
<!-- ErrorStack -->
<div ng-if="errorStack != null">
    <p>
        <span class="key">ErrorStack: </span>
    </p>
    <div class="json-container" ng-if="isErrorStackJson">
        <json-formatter json="errorStack" open="1"></json-formatter>
    </div>
    <div class="json-container" ng-if="!isErrorStackJson">
        <span>{{errorStack}}</span>
    </div>
</div>
