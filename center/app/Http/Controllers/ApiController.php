<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Http\Response;
use App\Http\Services\ApiService;
use App\Http\Utility\EnvUtility;


class ApiController extends Controller
{

    public function cve(Request $request)
    {
        $uri = $request->query('uri');
        $query = $request->query();
        unset($query['uri']);
        $result = ApiService::doGet(EnvUtility::CVE_API(), $uri, $query);
        header("Content-type: application/json");
        return response()->json($result);
    }

}