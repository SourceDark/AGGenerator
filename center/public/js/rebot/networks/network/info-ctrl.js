agbotApp.config(function($stateProvider) {
    $stateProvider.state({
        name: 'network.info',
        url: '/info',
        templateUrl: 'html/networks/network/info',
        controller: function ($scope, $http, $stateParams, $filter, $timeout) {
            $http
                .get(['api', 'networks', $stateParams.networkId, 'scores'].join('/'))
                .then(function (result) {
                    var xAxis = {
                        categories: [],
                        crosshair: true
                    };

                    for (var key in result.data)
                        xAxis.categories.push(key);

                    var series = [];
                    for (var key in result.data[xAxis.categories[0]]) {
                        var tp = {
                            name: key,
                            data: []
                        };
                        if (key === 'attack_path_count')
                            tp.yAxis = 1;
                        xAxis.categories.forEach(function (d) {
                            tp.data.push(parseInt(result.data[d][key]));
                        });
                        series.push(tp);
                    }

                    xAxis.categories = xAxis.categories.map(function (t) {
                        return new $filter('date')(t,'yyyy-MM-dd HH:mm:ss');
                    });
                    $scope.chartConfig = {
                        chart: {
                            zoomType: 'xy'
                        },
                        title: {
                            text: 'Score History (Last Week)'
                        },
                        xAxis: xAxis,
                        yAxis: [{ // Primary yAxis
                            labels: {
                                format: '',
                                style: {
                                    color: Highcharts.getOptions().colors[1]
                                }
                            },
                            title: {
                                text: 'Score',
                                style: {
                                    color: Highcharts.getOptions().colors[1]
                                }
                            }
                        }, { // Secondary yAxis
                            title: {
                                text: 'Paths',
                                style: {
                                    color: Highcharts.getOptions().colors[0]
                                }
                            },
                            labels: {
                                format: '',
                                style: {
                                    color: Highcharts.getOptions().colors[0]
                                }
                            },
                            opposite: true
                        }],
                        tooltip: {
                            shared: true
                        },
                        credits: {
                            text: '',
                            href: ''
                        },
                        series: series
                    };
                    $timeout(function () {
                        console.log($('#chart').width());
                        $scope.chart = Highcharts.chart('chart', $scope.chartConfig);
                    }, 1000);


                }, function (result) {
                    console.error('系统错误');
                });
        }
    });
});