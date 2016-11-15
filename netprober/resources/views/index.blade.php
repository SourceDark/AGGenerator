<html ng-app="aggeneratorApp">
    <head>
        <script src="js/lib/angular.min.js"></script>
        <script src="js/aggenerator/index.js"></script>
    </head>
    <body>
        <div ng-controller="indexController">
            <p>
                目标：
                <input ng-model="target"/>
            </p>
            <button ng-click="lightProbe()">轻度扫描</button>
            <button ng-click="deepProbe()">深度扫描</button>
            <div ng-show="currentStatus==Status.LightProbing">轻度扫描中……</div>
            <div ng-show="currentStatus==Status.DeepProbing">深度扫描中……</div>
            <div ng-show="currentStatus==Status.LightProbed">
                <table>
                    <thead>
                        <td>#</td>
                        <td>ip地址</td>
                        <td>mac地址</td>
                    </thead>
                    <tbody>
                        <tr ng-repeat="host in hosts">
                            <td ng-bind="$index"></td>
                            <td ng-bind="host.ip"></td>
                            <td ng-bind="host.mac"></td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>