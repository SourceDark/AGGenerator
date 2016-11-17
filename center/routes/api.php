<?php

use Illuminate\Http\Request;
use App\Http\Controllers\SensorController;
use App\Http\Controllers\VulnerabilityController;

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


Route::post('/sensor/record', function (Request $request) {
    $sensor_name = $request->input('sensor_name');
    $host_ip = $request->input('host_ip');
    $vulnerabilities = json_decode($request->input('vulnerabilities'));

    $sensor = SensorController::getSensorByName($sensor_name);
    $vul_report = VulnerabilityController::createVulReportBySensorAndHostIp($sensor, $host_ip);

    foreach ($vulnerabilities as $vulnerability) {
        VulnerabilityController::createVulReportRecordByRecordAndVulReportId($vulnerability, $vul_report->id);
    }

    return response()->json($vul_report -> id, 200);
});
