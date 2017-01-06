<?php

namespace App\Http\Middleware;
use App\Http\Utility\EnvUtility;

use Closure;

class ApiRedirect
{
    /**
     * Handle an incoming request.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \Closure  $next
     * @return mixed
     */
    public function handle($request, Closure $next)
    {
        $uri = $request -> getRequestUri();
        if (strcmp(substr($uri, 0, 4), '/api') != 0) {
            return $next($request);
        }
        if(strcmp(substr($uri, 0, 8), '/api/cve') == 0) {
            $host = EnvUtility::CVE_API();
            $uri = substr($uri, 8, strlen($uri) - 8);
        } else {
            $host = EnvUtility::ALGORITHM_API();
            $uri = substr($uri, 4, strlen($uri) - 4);
        }
        $api = $host . $uri;
        if ($request->isMethod('get')){
            $sFile = json_decode(file_get_contents($api));
            return response()->json($sFile);
        }

        if ($request->isMethod('post')){
            $data= json_decode($request->instance()->getContent());
            $content = http_build_query($data);
            $content_length = strlen($content);
            $opts = array(
                'http'=>array(
                    'method'=>"POST",
                    'ignore_errors' => true,
                    'header'=>"Content-type: application/x-www-form-urlencoded\r\n".
                                 "Content-length:".$content_length."\r\n" .
                                 "Cookie: foo=bar\r\n" .
                                 "\r\n",
                    'content' => $content
                )
            );
            $cxContext = stream_context_create($opts);

            $sFile = file_get_contents($api, false, $cxContext);
            return response()->json($sFile);
        }

        if ($request->isMethod('delete')){
            $opts = array(
                'http'=>array(
                    'method'=>"DELETE"
                )
            );
            $cxContext = stream_context_create($opts);
            $sFile = file_get_contents($api, false, $cxContext);
            return response()->json($sFile);
        }

        if ($request->isMethod('put')){
            $data= json_decode($request->instance()->getContent());
            $content = http_build_query($data);
            $content_length = strlen($content);
            $opts = array(
                'http'=>array(
                    'method'=>"PUT",
                    'ignore_errors' => true,
                    'header'=>"Content-type: application/x-www-form-urlencoded\r\n".
                                 "Content-length:".$content_length."\r\n" .
                                 "Cookie: foo=bar\r\n" .
                                 "\r\n",
                    'content' => $content
                )
            );
            $cxContext = stream_context_create($opts);
            $sFile = file_get_contents($api, false, $cxContext);
            return response()->json($sFile,200);
        }
    }
}
