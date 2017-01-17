agbotApp.config(function($stateProvider) {
    $stateProvider.state({
        name: 'network',
        url: '/network/{networkId:[0-9]+}',
        templateUrl: 'html/networks/network',
        controller: function ($scope, $http, $stateParams, $state) {
            $scope.loadNetwork = function (networkId) {
                var networks = {
                    1: {
                        id: 1,
                        name: 'serc-1730',
                        sensors: [
                            {
                                name: 'xr_test',
                                vulnerabilities: 17,
                                hosts: [
                                    {
                                        name: '192.168.200.1',
                                        vulnerabilities: 13
                                    },
                                    {
                                        name: '192.168.200.1',
                                        vulnerabilities: 4
                                    }
                                ]
                            }
                        ],
                        hosts: 11,
                        vulnerabilities: 13,
                        score: 7.6,
                        scoreHistory: [
                            {
                                date: 1484479918000,
                                score: 7.6
                            },
                            {
                                date: 1484393518000,
                                score: 7.6
                            },
                            {
                                date: 1484307118000,
                                score: 7.6
                            },
                            {
                                date: 1484220718000,
                                score: 7.6
                            },
                            {
                                date: 1484134318000,
                                score: 7.6
                            },
                            {
                                date: 1484047918000,
                                score: 7.6
                            },
                            {
                                date: 1483961518000,
                                score: 7.6
                            }
                        ]
                    },
                    2: {
                        id: 2,
                        name: 'beidasoft',
                        sensors: [
                            {
                                name: 'beidasoft',
                                vulnerabilities: 1,
                                hosts: []
                            },
                            {
                                name: 'beidasoft1',
                                vulnerabilities: 1,
                                hosts: []
                            },
                            {
                                name: 'beidasoft2',
                                vulnerabilities: 1,
                                hosts: []
                            }
                        ],
                        hosts: 114,
                        vulnerabilities: 18,
                        score: 9.5
                    }
                };
                $scope.network = networks[networkId];
            };
            $scope.loadNetwork($stateParams['networkId']);
            if ($state.current.name == "network") {
                $state.go("network.info");
            }
        }
    });
});