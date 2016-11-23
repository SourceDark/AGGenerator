/**
 * Created by Nettle on 2016/11/23.
 */

var attackGraphData = {
    "nodes": [
        {
            "id": 1,
            "info": "execCode(test,someUser)",
            "type": "OR",
            "initial": 0
        },
        {
            "id": 2,
            "info": "RULE 2 (remote exploit of a server program)",
            "type": "AND",
            "initial": 0
        },
        {
            "id": 3,
            "info": "netAccess(test,someProtocol,somePort)",
            "type": "OR",
            "initial": 0
        },
        {
            "id": 4,
            "info": "RULE 6 (direct network access)",
            "type": "AND",
            "initial": 0
        },
        {
            "id": 5,
            "info": "hacl(internet,test,someProtocol,somePort)",
            "type": "LEAF",
            "initial": 1
        },
        {
            "id": 6,
            "info": "attackerLocated(internet)",
            "type": "LEAF",
            "initial": 1
        },
        {
            "id": 7,
            "info": "networkServiceInfo(test,safari,someProtocol,somePort,someUser)",
            "type": "LEAF",
            "initial": 1
        },
        {
            "id": 8,
            "info": "vulExists(test,’CVE-2015-4000’,safari,remoteExploit,privEscalation)",
            "type": "LEAF",
            "initial": 1
        }

    ],
    "edges": [
        {
            "source": 5,
            "target": 4
        },
        {
            "source": 6,
            "target": 4
        },
        {
            "source": 4,
            "target": 3
        },
        {
            "source": 3,
            "target": 2
        },
        {
            "source": 7,
            "target": 2
        },
        {
            "source": 8,
            "target": 2
        },
        {
            "source": 2,
            "target": 1
        }
    ]
};