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

    static public function doPOST($api, $uri, $data, $query) {
          $queryStr = "";
          if (!empty($query)) {
              $queryStr = http_build_query($query);
          }
          $content = http_build_query($data);
          $content_length = strlen($content);
          $opts = stream_context_create(array(
              'http' => array(
                  "method" => "POST",
                  "ignore_errors" => true,
                  'header'=>"Content-type: application/x-www-form-urlencoded\r\n".
                                 "Content-length:".$content_length."\r\n" .
                                 "Cookie: foo=bar\r\n" .
                                 "\r\n",
                  'content' => $content
              )
          ));
          $str = file_get_contents($api.$uri.'?'.$queryStr, false, $opts);
          return json_decode($str);
    }

}
