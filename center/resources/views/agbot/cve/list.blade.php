<br>
<div class="container">
	<div class="panel panel-default" ng-repeat="cve in cves.content">	
		<div class="panel-heading">
	    <h3 class="panel-title"><a ui-sref="cve({cve_id:cve.id})" ng-bind="cve.id">Panel title</a>
				<small class="text-muted pull-right"><span ng-bind="cve.publishTime | date:'yyyy-MM-dd HH:mm:ss'"></span></small>
	    </h3>
	  </div>
	  <div class="panel-body">
	    <p ng-bind="cve.description">...</p>
	  </div>
	</div>
	

	<nav aria-label="Page navigation">
	  <ul class="pagination">
	    <li ng-class="{disabled: cves.first}">
	      <a ui-sref="cves({page:1})" aria-label="Previous">
	        <span aria-hidden="true">&laquo;</span>
	      </a>
	    </li>
	    <li ng-repeat="page in pages" ng-class="{active: page == currentPage}">
	    	<a ui-sref="cves({page:page})" ng-bind="page">1</a>
	    </li>
	    <li ng-class="{disabled: cves.last}">
	      <a ui-sref="cves({page:cves.totalPages})" aria-label="Next">
	        <span aria-hidden="true">&raquo;</span>
	      </a>
	    </li>
	  </ul>
	</nav>
</div>