/**
 * Created by Nettle on 2017/4/4.
 */

var attack_graph_test_data = {
    "id":77,
    "createdTime":1483609260000,
    "updatedTime":1483609260000,
    "status":"success",
    "containerId":"698ff71c791378310f49f27046c679200bbce14deb4cea3cedd97fa9152ff976",
    "algorithm":{
        "id":7,
        "name":"stl_attack_path_analysis",
        "description":null
    },
    "inputType":"attack_graph2.0",
    "outputType":"attack_path_list",
    "input":"{\"nodes\":[{\"id\":\"1\",\"info\":\"execCode(beidasoft_192_168_3_202,someUser)\",\"type\":\"OR\",\"initial\":\"0\",\"target\":false,\"attacker\":false},{\"id\":\"2\",\"info\":\"RULE 2 (remote exploit of a server program)\",\"type\":\"AND\",\"initial\":\"0\",\"target\":false,\"attacker\":false},{\"id\":\"3\",\"info\":\"netAccess(beidasoft_192_168_3_202,someProtocol,somePort)\",\"type\":\"OR\",\"initial\":\"0\",\"target\":false,\"attacker\":false},{\"id\":\"6\",\"info\":\"RULE 6 (direct network access)\",\"type\":\"AND\",\"initial\":\"0\",\"target\":false,\"attacker\":false},{\"id\":\"7\",\"info\":\"hacl(internate,beidasoft_192_168_3_202,someProtocol,somePort)\",\"type\":\"LEAF\",\"initial\":\"1\",\"target\":false,\"attacker\":false},{\"id\":\"8\",\"info\":\"attackerLocated(internate)\",\"type\":\"LEAF\",\"initial\":\"1\",\"target\":false,\"attacker\":true},{\"id\":\"9\",\"info\":\"RULE 6 (direct network access)\",\"type\":\"AND\",\"initial\":\"0\",\"target\":false,\"attacker\":false},{\"id\":\"10\",\"info\":\"hacl(tutorist,beidasoft_192_168_3_202,someProtocol,somePort)\",\"type\":\"LEAF\",\"initial\":\"1\",\"target\":false,\"attacker\":false},{\"id\":\"11\",\"info\":\"attackerLocated(tutorist)\",\"type\":\"LEAF\",\"initial\":\"1\",\"target\":false,\"attacker\":true},{\"id\":\"12\",\"info\":\"networkServiceInfo(beidasoft_192_168_3_202,mysql,someProtocol,somePort,someUser)\",\"type\":\"LEAF\",\"initial\":\"1\",\"target\":false,\"attacker\":false},{\"id\":\"13\",\"info\":\"vulExists(beidasoft_192_168_3_202,'CVE-2015-2617',mysql,remoteExploit,privEscalation)\",\"type\":\"LEAF\",\"initial\":\"1\",\"target\":false,\"attacker\":false},{\"id\":\"14\",\"info\":\"RULE 2 (remote exploit of a server program)\",\"type\":\"AND\",\"initial\":\"0\",\"target\":false,\"attacker\":false},{\"id\":\"15\",\"info\":\"vulExists(beidasoft_192_168_3_202,'CVE-2015-2639',mysql,remoteExploit,privEscalation)\",\"type\":\"LEAF\",\"initial\":\"1\",\"target\":false,\"attacker\":false},{\"id\":\"16\",\"info\":\"RULE 2 (remote exploit of a server program)\",\"type\":\"AND\",\"initial\":\"0\",\"target\":false,\"attacker\":false},{\"id\":\"17\",\"info\":\"vulExists(beidasoft_192_168_3_202,'CVE-2015-4830',mysql,remoteExploit,privEscalation)\",\"type\":\"LEAF\",\"initial\":\"1\",\"target\":false,\"attacker\":false}],\"edges\":[{\"source\":\"7\",\"target\":\"6\"},{\"source\":\"8\",\"target\":\"6\"},{\"source\":\"6\",\"target\":\"3\"},{\"source\":\"10\",\"target\":\"9\"},{\"source\":\"11\",\"target\":\"9\"},{\"source\":\"9\",\"target\":\"3\"},{\"source\":\"3\",\"target\":\"2\"},{\"source\":\"12\",\"target\":\"2\"},{\"source\":\"13\",\"target\":\"2\"},{\"source\":\"2\",\"target\":\"1\"},{\"source\":\"3\",\"target\":\"14\"},{\"source\":\"12\",\"target\":\"14\"},{\"source\":\"15\",\"target\":\"14\"},{\"source\":\"14\",\"target\":\"1\"},{\"source\":\"3\",\"target\":\"16\"},{\"source\":\"12\",\"target\":\"16\"},{\"source\":\"17\",\"target\":\"16\"},{\"source\":\"16\",\"target\":\"1\"}]}",
    "output":"[[1,2,3,12,13,6,7,8],[1,2,3,12,13,9,10,11],[1,2,3,14,12,15,6,7,8],[1,2,3,14,12,15,9,10,11],[1,2,3,16,12,17,6,7,8],[1,2,3,16,12,17,9,10,11]]",
    "errorStack":null,
    "inputTaskId":null,
    "parentTaskId":null
};