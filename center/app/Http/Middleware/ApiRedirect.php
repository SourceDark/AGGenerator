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
            $api = $host . $uri;
        } else {
            $host = EnvUtility::ALGORITHM_API();
            $uri = substr($uri, 4, strlen($uri) - 4);
            $api = $host . $uri;
        }
        if ($request->isMethod('get')){
            $sFile = json_decode(file_get_contents($api));
            return response()->json($sFile);
        }

        if ($request->isMethod('post')){
            $data= $request->instance()->getContent();
            $opts = array(
                'http'=>array(
                    'method'=>"POST",
                    'ignore_errors' => true,
                    'header'=>"Content-type: application/json; charset=utf-8",  //x-www-form-urlencoded
                    'content' => $data
                )
            );
            $cxContext = stream_context_create($opts);

            $sFile = file_get_contents($api, false, $cxContext);
            return response()->json($sFile);
        }

        if ($request->isMethod('delete')){
            $data= $request->instance()->getContent();
            if ($data == null){
                $opts = array(
                    'http'=>array(
                        'method'=>"DELETE",
                        'header'=>"Content-type: application/json; charset=utf-8", //x-www-form-urlencoded
                    )
                );
                $cxContext = stream_context_create($opts);
                $sFile = file_get_contents($api, false, $cxContext);
                return response()->json($sFile);
            }
        }

        if ($request->isMethod('put')){
            $data= $request->instance()->getContent();
            $opts = array(
                'http'=>array(
                    'method'=>"PUT",
                    'header'=>"Content-type: application/json; charset=utf-8",  //x-www-form-urlencoded
                    'content' => $data
                )
            );
            $cxContext = stream_context_create($opts);
            $sFile = file_get_contents($api, false, $cxContext);
            return response()->json($sFile,200);
        }
    }
}
