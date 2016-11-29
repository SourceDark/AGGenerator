<?php

use Illuminate\Http\Request;
use App\Http\Services\SensorService;
use App\Http\Services\VulnerabilityService;
use App\Http\Services\AlgorithmService;
use App\Http\Services\AlgorithmTaskService;

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


Route::group(['prefix' => '/algorithms'], function () {
    Route::get('/', function (Request $request) {
        return response()->json(
            array(
                "generation" => AlgorithmService::getGenerationAlgorithms(),
                "analysis" => AlgorithmService::getAnalysisAlgorithms()
            )
        );
    });
    Route::group(['prefix' => '{algorithm_id}'], function () {
        Route::get('/', function ($algorithm_id) {
            return response()->json(AlgorithmService::getAlgorithmById($algorithm_id));
        });
        Route::group(['prefix' => 'results'], function () {
            Route::get('/', function ($algorithm_id) {
                return response()->json(AlgorithmService::getResultsByAlgorithmId($algorithm_id));
            });
            Route::post('/', function (Request $request, $algorithm_id) {
                $content = $request->input('content');
                return response()->json(AlgorithmService::createResultsByAlgorithmIdAndContent($algorithm_id, $content));
            });
            Route::group(['prefix' => '{result_id}'], function () {
                Route::get('/', function ($algorithm_id, $result_id) {
                    return response()->json(AlgorithmService::getResultByAlgorithmIdAndResultId($algorithm_id, $result_id));
                });
                Route::group(['prefix' => 'analysis'], function() {
                    Route::post('/', 'AlgorithmController@createAnalysisByGenerationResult');
                    Route::get('/', function ($algorithm_id, $result_id) {
                        return response()->json(AlgorithmTaskService::getTasksByGen($algorithm_id, $result_id));
                    });
                });
            });
        });
    });
});

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
