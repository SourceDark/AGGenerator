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

    public static function lightProbe($target) {
        exec('nmap -v -sn ' . $target, $results);
        // Try remove binary data
        $results[1] = '';
        $hosts = array();
        for ($i = 0; $i < count($results); $i++) {
            $result = $results[$i];
            //echo $result . ' ' . strpos($result, 'Host is up') . ' ';
            if (strpos($result, 'Host is up') !== false) {
                $temp_res = $results[$i - 1];
                $ip = str_replace('Nmap scan report for ', "", $temp_res);
                $temp_res = $results[$i + 1];
                $mac = str_replace('AC Address: ', "", $temp_res);
                array_push($hosts, array(
                    'ip' => $ip,
                    'mac' => $mac
                ));
            }
        }
        return array(
            'target' => $target,
            'terminal_results' => $results,
            'alive_hosts_count' => count($hosts),
            'alive_hosts' => $hosts
        );
    }
}
