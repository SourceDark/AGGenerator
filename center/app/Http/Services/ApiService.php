<?php

namespace App\Http\Services;

class ApiService
{
    static public function doGet($api, $uri, $query) {
        $queryStr = "";
        if (!empty($query)) {
            $queryStr = http_build_query($query);
        }
        $str = file_get_contents($api.$uri.'?'.$queryStr, false, stream_context_create(array(
                    "http" => array(
                        "method" => "GET"
                    )
                )));
        return json_decode($str);
    }

}
