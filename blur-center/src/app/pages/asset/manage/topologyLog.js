/**
 * Created by Nettle on 2017/3/14.
 */

var topoData = [
        {
            "mac": "10:2A:B3:D4:71:AC (Unknown)",
            "services": [],
            "outer_interface": "",
            "gateway": "192.168.31.1",
            "inner_interface": "192.168.31.131"
        },
        {
            "mac": "8C:0D:76:F4:EE:5A (Unknown)",
            "services": [],
            "outer_interface": "",
            "gateway": "192.168.31.1",
            "inner_interface": "192.168.31.133"
        },
        {
            "mac": "54:19:C8:B4:15:8F (Unknown)",
            "services": [],
            "outer_interface": "",
            "gateway": "192.168.31.1",
            "inner_interface": "192.168.31.66"
        },
        {
            "mac": "9C:4E:36:9F:54:94 (Intel",
            "services": [
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "135",
                    "name": "msrpc"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "139",
                    "name": "netbios-ssn"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "445",
                    "name": "microsoft-ds"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "3389",
                    "name": "ms-wbt-server"
                }
            ],
            "outer_interface": "",
            "gateway": "192.168.31.1",
            "inner_interface": "192.168.31.26"
        },
        {
            "mac": "00:0C:29:95:BF:3D (VMware)",
            "services": [
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "21",
                    "name": "ftp"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "22",
                    "name": "ssh"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "80",
                    "name": "http"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "5051",
                    "name": "ida-agent"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "8000",
                    "name": "http-alt"
                }
            ],
            "outer_interface": "",
            "gateway": "192.168.200.2",
            "inner_interface": "192.168.200.160"
        },
        {
            "mac": "",
            "services": [
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "22",
                    "name": "ssh"
                }
            ],
            "outer_interface": "",
            "gateway": "192.168.200.2",
            "inner_interface": "192.168.200.101"
        },
        {
            "mac": "00:0C:29:DA:BE:7B (VMware)",
            "services": [
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "22",
                    "name": "ssh"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "53",
                    "name": "domain"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "80",
                    "name": "http"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "139",
                    "name": "netbios-ssn"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "445",
                    "name": "microsoft-ds"
                }
            ],
            "outer_interface": "",
            "gateway": "192.168.200.2",
            "inner_interface": "192.168.200.204"
        },
        {
            "mac": "00:0C:29:21:6E:95 (VMware)",
            "services": [
                {
                    "status": "closed",
                    "protocol": "tcp",
                    "port": "22",
                    "name": "ssh"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "80",
                    "name": "http"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "427",
                    "name": "svrloc"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "443",
                    "name": "https"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "8000",
                    "name": "http-alt"
                },
                {
                    "status": "closed",
                    "protocol": "tcp",
                    "port": "8080",
                    "name": "http-proxy"
                }
            ],
            "outer_interface": "",
            "gateway": "192.168.200.2",
            "inner_interface": "192.168.200.202"
        },
        {
            "mac": "48:4D:7E:AC:D7:C8 (Unknown)",
            "services": [],
            "outer_interface": "",
            "gateway": "192.168.200.2",
            "inner_interface": "192.168.200.59"
        },
        {
            "mac": "00:0C:29:01:B7:25 (VMware)",
            "services": [
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "22",
                    "name": "ssh"
                }
            ],
            "outer_interface": "",
            "gateway": "192.168.200.2",
            "inner_interface": "192.168.200.168"
        },
        {
            "mac": "00:0C:29:C6:32:B6 (VMware)",
            "services": [
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "22",
                    "name": "ssh"
                }
            ],
            "outer_interface": "",
            "gateway": "192.168.200.2",
            "inner_interface": "192.168.200.167"
        },
        {
            "mac": "20:4E:7F:71:C2:B0 (Netgear)",
            "services": [
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "8443",
                    "name": "https-alt"
                }
            ],
            "outer_interface": "",
            "gateway": "192.168.200.2",
            "inner_interface": "192.168.200.56"
        },
        {
            "mac": "48:4D:7E:AC:D8:5E (Unknown)",
            "services": [
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "443",
                    "name": "https"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "3389",
                    "name": "ms-wbt-server"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "5357",
                    "name": "wsdapi"
                }
            ],
            "outer_interface": "",
            "gateway": "192.168.200.2",
            "inner_interface": "192.168.200.57"
        },
        {
            "mac": "FC:4D:D4:F8:A7:7E (Universal",
            "services": [
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "22",
                    "name": "ssh"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "3306",
                    "name": "mysql"
                }
            ],
            "outer_interface": "",
            "gateway": "192.168.200.2",
            "inner_interface": "192.168.200.51"
        },
        {
            "mac": "FC:4D:D4:F8:AA:1A (Universal",
            "services": [
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "5357",
                    "name": "wsdapi"
                }
            ],
            "outer_interface": "",
            "gateway": "192.168.200.2",
            "inner_interface": "192.168.200.53"
        },
        {
            "mac": "64:00:6A:70:CC:FB (Unknown)",
            "services": [
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "80",
                    "name": "http"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "443",
                    "name": "https"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "3306",
                    "name": "mysql"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "5357",
                    "name": "wsdapi"
                }
            ],
            "outer_interface": "",
            "gateway": "192.168.200.2",
            "inner_interface": "192.168.200.55"
        },
        {
            "mac": "40:F2:E9:63:FA:88 (IBM)",
            "services": [
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "22",
                    "name": "ssh"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "80",
                    "name": "http"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "427",
                    "name": "svrloc"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "443",
                    "name": "https"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "8000",
                    "name": "http-alt"
                },
                {
                    "status": "closed",
                    "protocol": "tcp",
                    "port": "8080",
                    "name": "http-proxy"
                }
            ],
            "outer_interface": "",
            "gateway": "192.168.200.2",
            "inner_interface": "192.168.200.10"
        },
        {
            "mac": "30:B4:9E:44:14:DB (Unknown)",
            "services": [
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "135",
                    "name": "msrpc"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "139",
                    "name": "netbios-ssn"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "445",
                    "name": "microsoft-ds"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "5357",
                    "name": "wsdapi"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "10000",
                    "name": "snet-sensor-mgmt"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "49152",
                    "name": "unknown"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "49153",
                    "name": "unknown"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "49154",
                    "name": "unknown"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "49155",
                    "name": "unknown"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "49156",
                    "name": "unknown"
                }
            ],
            "outer_interface": "",
            "gateway": "192.168.31.1",
            "inner_interface": "192.168.31.242"
        },
        {
            "mac": "98:01:A7:9B:AA:B5 (Unknown)",
            "services": [
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "3306",
                    "name": "mysql"
                }
            ],
            "outer_interface": "",
            "gateway": "192.168.31.1",
            "inner_interface": "192.168.31.100"
        },
        {
            "mac": "40:F2:E9:63:F9:FC (IBM)",
            "services": [
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "22",
                    "name": "ssh"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "80",
                    "name": "http"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "427",
                    "name": "svrloc"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "443",
                    "name": "https"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "8000",
                    "name": "http-alt"
                },
                {
                    "status": "closed",
                    "protocol": "tcp",
                    "port": "8080",
                    "name": "http-proxy"
                }
            ],
            "outer_interface": "",
            "gateway": "192.168.200.2",
            "inner_interface": "192.168.200.11"
        },
        {
            "mac": "F0:DE:F1:75:6B:E7 (Wistron",
            "services": [
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "80",
                    "name": "http"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "135",
                    "name": "msrpc"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "139",
                    "name": "netbios-ssn"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "443",
                    "name": "https"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "445",
                    "name": "microsoft-ds"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "515",
                    "name": "printer"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "3389",
                    "name": "ms-wbt-server"
                }
            ],
            "outer_interface": "",
            "gateway": "192.168.200.2",
            "inner_interface": "192.168.200.66"
        },
        {
            "mac": "F4:5C:89:C1:50:0B (Unknown)",
            "services": [
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "22",
                    "name": "ssh"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "80",
                    "name": "http"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "88",
                    "name": "kerberos-sec"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "445",
                    "name": "microsoft-ds"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "548",
                    "name": "afp"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "3306",
                    "name": "mysql"
                }
            ],
            "outer_interface": "",
            "gateway": "192.168.31.1",
            "inner_interface": "192.168.31.208"
        },
        {
            "mac": "",
            "services": [
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "22",
                    "name": "ssh"
                }
            ],
            "outer_interface": "",
            "gateway": "192.168.31.1",
            "inner_interface": "192.168.31.73"
        },
        {
            "mac": "30:B4:9E:2F:DF:E5 (Unknown)",
            "services": [],
            "outer_interface": "",
            "gateway": "192.168.31.1",
            "inner_interface": "192.168.31.18"
        },
        {
            "mac": "74:E5:0B:3A:DD:B4 (Intel",
            "services": [
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "22",
                    "name": "ssh"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "23",
                    "name": "telnet"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "80",
                    "name": "http"
                }
            ],
            "outer_interface": "",
            "gateway": "192.168.31.1",
            "inner_interface": "192.168.31.75"
        },
        {
            "mac": "2C:33:7A:61:A7:A1 (Unknown)",
            "services": [
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "135",
                    "name": "msrpc"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "139",
                    "name": "netbios-ssn"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "445",
                    "name": "microsoft-ds"
                }
            ],
            "outer_interface": "",
            "gateway": "192.168.31.1",
            "inner_interface": "192.168.31.76"
        },
        {
            "mac": "00:0C:29:22:BC:16 (VMware)",
            "services": [
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "22",
                    "name": "ssh"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "3306",
                    "name": "mysql"
                }
            ],
            "outer_interface": "",
            "gateway": "192.168.200.2",
            "inner_interface": "192.168.200.86"
        },
        {
            "mac": "94:65:2D:22:33:E7 (Unknown)",
            "services": [],
            "outer_interface": "",
            "gateway": "192.168.31.1",
            "inner_interface": "192.168.31.10"
        },
        {
            "mac": "F4:0F:24:36:B6:96 (Unknown)",
            "services": [],
            "outer_interface": "",
            "gateway": "192.168.31.1",
            "inner_interface": "192.168.31.17"
        },
        {
            "mac": "00:0C:29:E2:C9:58 (VMware)",
            "services": [
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "22",
                    "name": "ssh"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "80",
                    "name": "http"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "427",
                    "name": "svrloc"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "443",
                    "name": "https"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "8000",
                    "name": "http-alt"
                },
                {
                    "status": "closed",
                    "protocol": "tcp",
                    "port": "8080",
                    "name": "http-proxy"
                }
            ],
            "outer_interface": "",
            "gateway": "192.168.200.2",
            "inner_interface": "192.168.200.203"
        },
        {
            "mac": "00:0C:29:34:A8:37 (VMware)",
            "services": [
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "22",
                    "name": "ssh"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "80",
                    "name": "http"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "443",
                    "name": "https"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "3128",
                    "name": "squid-http"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "3306",
                    "name": "mysql"
                }
            ],
            "outer_interface": "",
            "gateway": "192.168.200.2",
            "inner_interface": "192.168.200.156"
        },
        {
            "mac": "00:0C:29:E2:05:F2 (VMware)",
            "services": [
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "22",
                    "name": "ssh"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "3306",
                    "name": "mysql"
                }
            ],
            "outer_interface": "",
            "gateway": "192.168.200.2",
            "inner_interface": "192.168.200.157"
        },
        {
            "mac": "00:0C:29:D7:1E:C5 (VMware)",
            "services": [
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "22",
                    "name": "ssh"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "25",
                    "name": "smtp"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "53",
                    "name": "domain"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "80",
                    "name": "http"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "139",
                    "name": "netbios-ssn"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "445",
                    "name": "microsoft-ds"
                }
            ],
            "outer_interface": "",
            "gateway": "192.168.200.2",
            "inner_interface": "192.168.200.155"
        },
        {
            "mac": "00:0C:29:2C:1B:1D (VMware)",
            "services": [
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "22",
                    "name": "ssh"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "10000",
                    "name": "snet-sensor-mgmt"
                }
            ],
            "outer_interface": "",
            "gateway": "192.168.200.2",
            "inner_interface": "192.168.200.151"
        },
        {
            "mac": "64:00:6A:70:E1:47 (Unknown)",
            "services": [
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "443",
                    "name": "https"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "5357",
                    "name": "wsdapi"
                }
            ],
            "outer_interface": "",
            "gateway": "192.168.200.2",
            "inner_interface": "192.168.200.61"
        },
        {
            "mac": "48:4D:7E:AC:D7:E9 (Unknown)",
            "services": [
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "443",
                    "name": "https"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "5357",
                    "name": "wsdapi"
                }
            ],
            "outer_interface": "",
            "gateway": "192.168.200.2",
            "inner_interface": "192.168.200.63"
        },
        {
            "mac": "48:4D:7E:AC:D4:AE (Unknown)",
            "services": [
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "5357",
                    "name": "wsdapi"
                }
            ],
            "outer_interface": "",
            "gateway": "192.168.200.2",
            "inner_interface": "192.168.200.62"
        },
        {
            "mac": "00:90:F5:8E:01:59 (Clevo",
            "services": [
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "135",
                    "name": "msrpc"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "139",
                    "name": "netbios-ssn"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "445",
                    "name": "microsoft-ds"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "3389",
                    "name": "ms-wbt-server"
                }
            ],
            "outer_interface": "",
            "gateway": "192.168.200.2",
            "inner_interface": "192.168.200.99"
        },
        {
            "mac": "00:0C:29:9B:61:A4 (VMware)",
            "services": [
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "22",
                    "name": "ssh"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "80",
                    "name": "http"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "443",
                    "name": "https"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "514",
                    "name": "shell"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "3000",
                    "name": "ppp"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "3128",
                    "name": "squid-http"
                },
                {
                    "status": "open",
                    "protocol": "tcp",
                    "port": "3306",
                    "name": "mysql"
                }
            ],
            "outer_interface": "",
            "gateway": "192.168.200.2",
            "inner_interface": "192.168.200.165"
        },
        {
            "services": [],
            "outer_interface": "192.168.200.5",
            "gateway": "192.168.200.2",
            "inner_interface": "192.168.31.1"
        },
        {
            "services": [],
            "outer_interface": "162.105.30.200",
            "gateway": "162.105.30.1",
            "inner_interface": "192.168.200.2"
        }
    ];