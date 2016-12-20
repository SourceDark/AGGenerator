<div class="container" id="algorithm-edit">
    <form name="algorithmForm" class="form-horizontal" role="form" novalidate>
        <div class="form-group">
            <label for="name" class="col-sm-2 control-label">Name</label>
            <div class="col-sm-9">
                <input ng-model="algorithm.name" name="name" ng-maxlength="255" class="form-control" required
                       ng-class="{true: 'invalid'}[algorithmForm.name.$invalid && algorithmForm.name.$dirty]"/>
            </div>
        </div>
        <div class="form-group">
            <label for="image" class="col-sm-2 control-label">Image</label>
            <div class="col-sm-9">
                <input ng-model="algorithm.image" name="image" ng-maxlength="255" class="form-control" required
                       ng-class="{true: 'invalid'}[algorithmForm.image.$invalid && algorithmForm.image.$dirty]"/>
            </div>
        </div>
        <div class="form-group">
            <label for="inputType" class="col-sm-2 control-label">Input Type</label>
            <div class="col-sm-9">
                <select ng-model="algorithm.inputType" name="inputType" class="form-control" ng-options="item.name as item.name for item in resultTypes" required></select>
            </div>
            <div class="col-sm-1" style="padding-left: 0px">
                <button type="button" class="btn btn-default" ng-click="newDataType('inputType')">
                    <span class="glyphicon glyphicon-plus"></span>New
                </button>
            </div>
        </div>
        <div class="form-group">
            <label for="outputType" class="col-sm-2 control-label">Output Type</label>
            <div class="col-sm-9">
                <select ng-model="algorithm.outputType" name="outputType" class="form-control" ng-options="item.name as item.name for item in resultTypes" required></select>
            </div>
            <div class="col-sm-1" style="padding-left: 0px">
                <button type="button" class="btn btn-default" ng-click="newDataType('outputType')">
                    <span class="glyphicon glyphicon-plus"></span>New
                </button>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-9 col-sm-offset-2">
                <button type="submit" class="btn btn-default" ng-click="submit()" ng-disabled="createResultType || algorithmForm.$invalid || !algorithmForm.$dirty">Submit</button>
            </div>
        </div>
    </form>
    <hr />
    <form name="resultTypeForm" ng-if="createResultType" class="form-horizontal" role="form">
        <h4>Create Result Type</h4>
        <div class="form-group">
            <label for="name" class="col-sm-2 control-label">Name</label>
            <div class="col-sm-9">
                <input ng-model="dataType.name" name="name" ng-maxlength="255" class="form-control" required
                       ng-class="{true: 'invalid'}[resultTypeForm.name.$invalid && resultTypeForm.name.$dirty]" />
            </div>
        </div>
        <div class="form-group">
            <label for="image" class="col-sm-2 control-label">Description</label>
            <div class="col-sm-9">
                <textarea ng-model="dataType.description" name="description" class="form-control"></textarea>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-9 col-sm-offset-2">
                <button type="submit" class="btn btn-default" ng-click="submitDataType()" ng-disabled="resultTypeForm.$invalid">Submit</button>
                <button type="submit" class="btn btn-default" ng-click="cancelDataType()">Cancel</button>
            </div>
        </div>
    </form>
</div>