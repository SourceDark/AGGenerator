<?php

use Illuminate\Http\Request;
use App\Http\Services\SensorService;
use App\Http\Services\VulnerabilityService;

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


Route::post('/sensor/reports', function (Request $request) {
    // Decode parameters
    $report = $request->input('report');

    // Find sensor, create a new sensor if not exists
    $sensor_name = $report['sensor'];
    $sensor = SensorService::getSensorByName($sensor_name);
    if ($sensor == null) {
        $sensor = SensorService::createSensorByName($sensor_name);
    }

    $vulnerabilities = $report['vulnerabilities'];
    foreach ($vulnerabilities as $ip => $ports) {
        $vul_report = VulnerabilityService::createVulReportBySensorIdAndHostIp($sensor->id, $ip);
        foreach ($ports as $port) {
            $record = array(
                'port_name' => $port['port']['port_name'],
                'port_proto' => $port['port']['proto'],
                'threat' => $port['threat'],
                'cves' => json_encode($port['nvt']['cves'])
            );
            VulnerabilityService::createVulReportRecordByRecordAndVulReportId($record, $vul_report->id);
        }
    }

    return response()->json("Reports created successfully.", 200);
});
