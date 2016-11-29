<div>
    <div class="infobar">
        <h3 ng-if="paths">Attack Path</h3>
        <div ng-if="paths" class="infobar-content">
            <div class="path_id" ng-click="showPath(-1)" ng-class="{true: 'active'}[selectedPathId==-1]">All Paths</div>
            <div class="path_id" ng-repeat="path in paths" ng-click="showPath($index)" ng-class="{true: 'active'}[$index==selectedPathId]">Path #{{$index+1}}<span>{{path.length}} nodes</span></div>
        </div>
        <h3>Selection Detail</h3>
        <div ng-if="selection" class="infobar-content">
            <p>ID: <span ng-bind="selection.id"></span></p>
            <p>Type: <span ng-bind="selection.type"></span></p>
            <p>Info: <span ng-bind="selection.info"></span></p>
        </div>
        <div ng-if="!selection" class="infobar-content">
            <p>Select an element in the visualization.</p>
        </div>
    </div>
    <svg id="attack-graph">
        <defs>
            <marker id="arrow" markerUnits="strokeWidth" markerWidth="12" markerHeight="12" viewBox="0 0 12 12" refX="25" refY="6" orient="auto">
                <path d="M2,2 L10,6 L2,10 L6,6 L2,2" fill="black"></path>
            </marker>
            <marker id="arrow_red" markerUnits="strokeWidth" markerWidth="12" markerHeight="12" viewBox="0 0 12 12" refX="25" refY="6" orient="auto">
                <path d="M2,2 L10,6 L2,10 L6,6 L2,2" fill="red"></path>
            </marker>
            <g id="leaf" transform="translate(-30 -30)">
                <rect width="60" height="60"></rect>
            </g>
            <g id="nonleaf">
                <circle r="30"></circle>
            </g>
            <g id="leaf_inner" transform="translate(-30 -30)">
                <rect width="52" height="52" x="4" y="4"></rect>
            </g>
            <g id="nonleaf_inner">
                <circle r="26"></circle>
            </g>
        </defs>
    </svg>
</div>