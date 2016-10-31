<?php

use Illuminate\Http\Request;
use App\Http\Utility\NmapUtility;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::get('/user', function (Request $request) {
    return $request->user();
})->middleware('auth:api');

Route::get('/nmap', function(Request $request) {
    $ip = $request->input('ip');
    exec('nmap -v -A ' . $ip, $results);
    $results[0] = $results[1] = "";
    return NmapUtility::analysisResults($results);
    /*
    return array(
        'query' => $ip,
        'quert_status' => 'failed',
        'terminal_outputs' => $results,
        'alive_hosts' => array(
            array(
                'host_ip' => '192.168.1.1',
                'host_config' => array(
                    'operating_system' => "unknown"
                ),
                'ports' => array(
                    array(
                        'port' => '80',
                        'service' => 'http'
                    ),
                    array(
                        'port' => ''
                    )
            ),

        )
    );*/
});