<div>
	  <br />
    <form class="container">
    	<h5><a ui-sref="cves({page:1})"> << 返回列表</a></h5>
    	<h4><span ng-bind="cve.id"></span></h4>
    	<p class="text-muted">发布时间： <span ng-bind="cve.publishTime | date:'yyyy-MM-dd HH:mm:ss'"></span></p>
		  <div class="panel panel-default">
		    <p class="panel-heading">描述</p>
		    <p  class="panel-body" ng-bind="cve.description"></p>
		  </div>
		  <div class="panel panel-default">
		    <p class="panel-heading">cvss评级</p>
		    <div class="panel-body"> 
		    	<ul>
						<li>Score:<span ng-bind="cve.cvssScore"></span></li>
			      <li>AccessComplexity：<span ng-bind="cve.cvssAccessComplexity"></span></li>
				    <li>AccessVector：<span ng-bind="cve.cvssAccessVector"></span></li>
				    <li>Authentication：<span ng-bind="cve.cvssAuthentication"></span></li>
				    <li>AvailabilityImpact：<span ng-bind="cve.cvssAvailabilityImpact"></span></li>
				    <li>ConfidentialityImpact：<span ng-bind="cve.cvssConfidentialityImpact"></span></li>
				    <li>IntegrityImpact：<span ng-bind="cve.cvssIntegrityImpact"></span></pli	
		    	</ul>
		    </div>
		  </div>
		  <div class="panel panel-default">
		    <p class="panel-heading">受影响软件</p>
		    <div  class="panel-body" >
		    	<ul>
			      <li ng-repeat="software in cve.softwares" ng-bind="software"></li>
			    </ul>
		    </div>
		  </div>
		  <div class="panel panel-default">
		    <p class="panel-heading">相关链接</p>
		    <div  class="panel-body" >
		    	<ul>
		    		<li><a target="_blank" ng-href="http://cve.mitre.org/cgi-bin/cvename.cgi?name={{cveId}}">cve官网</a></li>
			      <li ng-repeat="reference in cve.references">
			      	<a target="_blank" ng-href="{{reference.link}}" ng-bind="reference.title"></a>
			      </li>
			    </ul>
		    </div>
		  </div>
		</form>
</div>