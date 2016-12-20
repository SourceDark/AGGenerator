<?php

namespace App\Http\Middleware;

use Closure;

class Redirect
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
        if (strcmp(substr($uri, 0, 4), '/api') == 0) {
            if ($request->isMethod('get')){
                $sFile = json_decode(file_get_contents(env('ALGORITHM_API') . substr($uri, 4, strlen($uri) - 4)));

                return response($sFile);
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

                $sFile = file_get_contents(env('ALGORITHM_API') . substr($uri, 4, strlen($uri) - 4), false, $cxContext);
                if (strcmp($sFile,'{"status": "success"}')==0)
                    return response($sFile,200);
                else
                    return response($sFile,401);
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
                    $sFile = file_get_contents(env('ALGORITHM_API') . substr($uri, 4, strlen($uri) - 4), false, $cxContext);
                    return response($sFile);
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
                $sFile = file_get_contents(env('ALGORITHM_API') . substr($uri, 4, strlen($uri) - 4), false, $cxContext);
                if (strcmp($sFile,'{"status": "success"}')==0)
                    return response($sFile,200);
                else
                    return response($sFile,401);
            }

        }
        return $next($request);
    }
}
