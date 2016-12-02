<div ng-controller="createGenerationAlgorithmCtrl">
    <div class="container">
        <form>
          <div class="form-group">
            <label for="sensors">sensor select</label>
            <span ng-repeat="sensor in generation.sensors">
                <input type="checkbox" class="form-control" id="sensors" ng-model="snesor.checked">
                <span ng-bind="sensor.name"></span>
            </span>
          </div>

          <div class="form-group">
            <label for="hacl">host access controller list</label>
            <textarea ng-model="generation.hacl" id="hacl" class="form-control"></textarea>
          </div>
          <button type="submit" class="btn btn-default">Submit</button>
        </form>
    </div>
</div>