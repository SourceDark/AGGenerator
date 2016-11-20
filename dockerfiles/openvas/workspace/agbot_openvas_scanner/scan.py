from threading import Semaphore
from functools import partial
import json

from openvas_lib import VulnscanManager, VulnscanException

def print_status(i):
    print(str(i))

def launch_scanner(target):
    print("start scan " + target)
    sem = Semaphore(0)

    # Configure
    manager = VulnscanManager("localhost", "admin", "openvas")

    # Launch
    scan_id, target_id = manager.launch_scan(target = target,
                        profile = "Full and fast",
                        callback_end = partial(lambda x: x.release(), sem),
                        callback_progress = print_status)

    # Wait
    sem.acquire()

    # Finished scan
    print("finished")
    openvas_results = manager.get_results(scan_id)
    results = []
    for opr in openvas_results:
        results.append({
            "id": opr.id,
            "subnet": opr.subnet,
            "host": opr.host,
            "port": {
                "port_name": opr.port.port_name,
                "number": opr.port.number,
                "proto": opr.port.proto
            },
            "nvt": {
                "oid" : opr.nvt.oid,
                "name" : opr.nvt.name,
                "cvss_base" : opr.nvt.cvss_base,
                "cvss_base_vector" : opr.nvt.cvss_base_vector,
                "risk_factor" : opr.nvt.risk_factor,
                "category" : opr.nvt.category,
                "summary" : opr.nvt.summary,
                "description" : opr.nvt.description,
                "family" : opr.nvt.family,

                "cves" : opr.nvt.cve,
                "bids" : opr.nvt.bid,
                "bugtraqs" : opr.nvt.bugtraq,
                "xrefs" : opr.nvt.xrefs,
                "fingerprints" : opr.nvt.fingerprints,
                "tags" : opr.nvt.tags
            },
            "threat": opr.threat,
            "description": opr.raw_description,
            "notes": opr.notes,
            "overrides": opr.overrides,
            "impact": opr.impact,
            "summary": opr.summary,
            "vulnerability_insight": opr.vulnerability_insight,
            "affected_software": opr.affected_software,
            "solution": opr.solution
        })
    return results


def launch_scanners():
    ips = open('/data/input')
    result = {}
    for ip in  ips.readlines():
        try:
            result[ip] = launch_scanner(ip)
        except Exception, e:
            result[ip] = {"error": str(e)}
    return result

result = launch_scanners()
print(result)
json.dump(result, open('/data/output', 'w'))


