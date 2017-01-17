<table class="table mid">
    <thead>
        <tr>
            <td>Sensor</td>
            <td>IP</td>
            <td>Vulnerabilities</td>
            <td>Operation</td>
        </tr>
    </thead>
    <tbody>
        <a ui-sref="networks.network.hosts.newSensor">
            <button type="button" class="btn btn-default">
                <span class="glyphicon glyphicon-plus"></span> New Sensor
            </button>
        </a>
        <tr ng-repeat-start="sensor in sensors">
            <td style="border-top-color: #000;">{{sensor.name}}</td>
            <td style="border-top-color: #000;">{{sensor.ip}}</td>
            <td style="border-top-color: #000;">{{sensorVulnerabilitiesCount(sensor)}}</td>
            <td style="border-top-color: #000;"><i class="fa fa-times" aria-hidden="true"></i></td>
        </tr>
        <tr ng-repeat-end>
            <td>Hosts</td>
            <td colspan="5">
                <table class="table mid" style="margin-top: 0">
                    <thead>
                    <tr>
                        <td>Host</td>
                        <td>Vulnerabilities</td>
                    </tr>
                    </thead>
                    <tbody>
                    <tr ng-repeat="host in sensor.hosts">
                        <td>{{host.name}}</td>
                        <td>{{hostVulnerabilitiesCount(host)}}</td>
                    </tr>
                    </tbody>
                </table>
            </td>
        </tr>
    </tbody>
</table>