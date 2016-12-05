<div class="container">
    <form>
    	<h4 for="sensors">sensor select</h4>
      <div class="checkbox" ng-repeat="sensor in sensors">
		    <label>
		      <input type="checkbox" ng-model="sensor.checked"> <span ng-bind="sensor.name"></span>  
		    </label>
		  </div>

      <div class="form-group">
        <label for="hacl">host access controller list</label>
        <textarea ng-model="task.hacls" id="hacls" class="form-control"></textarea>
      </div>
      <button type="submit" class="btn btn-default" ng-click="submit()">Submit</button>
    </form>
</div>