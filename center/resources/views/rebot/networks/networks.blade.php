<div class="container">
    <p class="route-path"></p>
    <div class="board">
        <a ui-sref="algorithms.new">
            <button type="button" class="btn btn-default">
                <span class="glyphicon glyphicon-plus"></span> New Network
            </button>
        </a>
        <div ng-loading="status" ng-loading-empty="empty">
            <table class="table mid">
                <thead>
                    <tr>
                        <td>#</td>
                        <td>Name</td>
                        <td>Sensors</td>
                        <td>Hosts</td>
                        <td>Vulnerabilities</td>
                        <td>Score</td>
                    </tr>
                </thead>
                <tbody>
                    <tr ng-repeat="network in networks">
                        <td>{{network.id}}</td>
                        <td><a ui-sref="network({networkId:network.id})">{{network.name}}</a></td>
                        <td>{{network.sensors}}</td>
                        <td>{{network.hosts}}</td>
                        <td><a ui-sref="network.vulnerabilities({networkId:network.id})">{{network.vulnerabilities}}</a></td>
                        <td>{{network.score}}</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>