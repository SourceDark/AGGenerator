<?php

namespace App\Http\Utility;

class NmapUtility
{
    public static function analysisResults($results) {
        $hosts = array();
        for ($i = 0; $i < count($results); $i++) {
            $result = $results[$i];
            if (strpos($result, "Host is up") != false) {
                /*
                if (strpos($result, "[host down]") == false) {
                    $ip =
                }*/
                $temp_res = $results[$i - 1];
                $pos = str_replace($temp_res, "Nmap scan report for ", "");
                $ip = $temp_res;
                array_push($hosts, array(
                    'ip' => $ip
                ));
            }
        }
        return array(
            'terminal_results' => $results,
            'alive_hosts' => $hosts
        );
    }
}
