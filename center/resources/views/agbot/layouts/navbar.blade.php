<div class="navbar" ng-controller="navbarCtrl">
    <div class="container-fluid">
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1" style="padding-left: 1px;">
            <ul class="nav navbar-nav navbar-left">
                <li class="logo left" style="padding-left: 0px;">
                    <div class="logo-text">REBOT</div>
                </li>
                <li><div class="line"></div></li>
                <li><a href="" ui-sref="networks" ng-class="{active: inState('network')}">Vision</a></li>
                <li><a href="" ui-sref="algorithms.index" ng-class="{active: inState('algorithms')}">Algorithms</a></li>
                <li><a href="" ui-sref="tasks" ng-class="{active: inState('tasks') || inState('task')}">Tasks</a></li>
                <li><a href="" ui-sref="sensor" ui-sref-active="active">Sensor</a></li>
                <li><a href="" ui-sref="cves" ng-class="{active: inState('cves{page:1}')}">Vulnerabilities </a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="" ui-sref="attack_graph" ui-sref-active="active">Other researchers</a></li>
                <li><a href="#" ui-sref="aboutUs" ui-sref-active="active">About Us</a></li>
                <li><div class="line"></div></li>
                <li><a href="#"><i class="fa fa-question-circle" aria-hidden="true"></i> Help</a></li>
            </ul>
        </div>
    </div>
</div>