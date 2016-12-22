<div class="container" ng-controller="bayesianNetworkCtrl" id="bayes">
    <form name="bayesForm" class="form-horizontal" role="form" novalidate>
        <div class="form-group">
            <label for="algorithm_id" class="col-sm-2 control-label">Algorithm ID</label>
            <div class="col-sm-10">
                <select ng-model="task.algorithm_id" name="algorithm_id" class="form-control" ng-options="algo.id as algo.name for algo in algorithms" required
                        ng-change="selectAlgorithm()"
                ></select>
            </div>
        </div>
        <div class="form-group">
            <label for="result_id" class="col-sm-2 control-label">Result ID</label>
            <div class="col-sm-9">
                <select ng-model="task.result_id" name="result_id" class="form-control" ng-options="result.id as result.id for result in results" required ng-disabled="noResultFlag"></select>
            </div>
            <div class="col-sm-1" style="padding-left: 0px">
                <a  href="#/algorithms/{{task.algorithm_id}}/attack_graph/{{task.result_id}}" target="_blank"><button type="button" class="btn btn-default" style="width: 100%;"  ng-disabled="noResultFlag">
                    View
                </button></a>
            </div>
        </div>
        <div class="form-group">
            <label for="result_id" class="col-sm-2 control-label">Output Type</label>
            <div class="col-sm-10">
                <div ng-hide="ready" style="margin-top: 7px;">Loading Topology Graph ...</div>
                <topology-event-graph ng-show="ready" ready="ready" node-selected="nodeSelected"></topology-event-graph>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-10 col-sm-offset-2">
                <button type="submit" class="btn btn-default" ng-click="submit()" ng-disabled="bayesForm.$invalid || sending">Submit</button>
            </div>
        </div>
    </form>
</div>