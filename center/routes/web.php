<?php

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Blade::setContentTags('<%', '%>');
Blade::setEscapedContentTags('<%%', '%%>');


Route::get('ip', function() {
    $ip = $_SERVER["REMOTE_ADDR"];
    echo $ip;
});

/**
 * This part of router is for providing html fragments
 */
Route::group(['prefix' => 'html'], function () {
    Route::get('aboutUs', function ()    {
        return view('agbot.aboutUs.index');
    });
    Route::group(['prefix' => 'vision'], function () {
        Route::get('/', function () {
            return view('agbot.vision.index');
        });
        Route::get('/topology_template', function() {
            return view('agbot.vision.topologyTemplate');
        });
    });
    Route::get('sensor', function () {
        return view('agbot.sensor.index');
    });
    Route::get('attackGraph', function () {
        return view('agbot.attackGraph.index');
    });
    Route::group(['prefix' => 'algorithms'], function () {
        Route::get('/', function () {
            return view('agbot.algorithms.index');
        });
        Route::get('/algorithm', function() {
            return view('agbot.algorithms.algorithm');
        });
        Route::get('/algorithm_edit', function() {
            return view('agbot.algorithms.algorithmEdit');
        });
        Route::get('/algorithm_new_task/{id}', function($id) {
            return view('agbot.algorithms.task.'.$id);
        });
        Route::get('/result', function() {
            return view('agbot.algorithms.result.index');
        });
        Route::get('/result/info', function() {
            return view('agbot.algorithms.result.info');
        });
        Route::get('/result/tasks', function() {
            return view('agbot.algorithms.result.tasks');
        });
        Route::get('/result/newTask', function() {
            return view('agbot.algorithms.result.newTask');
        });
        Route::get('/attack_graph', function() {
            return view('agbot.algorithms.attackGraph');
        });
        Route::get('/attack_graph_template', function() {
            return view('agbot.algorithms.attackGraphTemplate');
        });
        Route::get('/tasks/new', function() {
            return view('agbot.algorithms.task.new');
        });
        Route::group(['prefix' => 'visualization'], function () {
            Route::get('/attack_graph_2', function() {
                return view('agbot.algorithms.visualization.attackGraph2');
            });
        });
    });
    Route::group(['prefix' => 'tasks'], function() {
        Route::get('/tasks', function() {
            return view('agbot.tasks.tasks');
        });
        Route::group(['prefix' => 'task'], function() {
            Route::get('/', function () {
                return view('agbot.tasks.task.index');
            });
            Route::get('/info', function () {
                return view('agbot.tasks.task.info');
            });
            Route::get('/export', function () {
                return view('agbot.tasks.task.export');
            });
        });
    });
    Route::group(['prefix' => 'networks'], function() {
        Route::get('/', function() {
            return view('rebot.networks.networks');
        });
        Route::group(['prefix' => 'network'], function() {
            Route::get('/', function() {
                return view('rebot.networks.network.network');
            });
            Route::get('/info', function() {
                return view('rebot.networks.network.info');
            });
            Route::get('/hosts', function() {
                return view('rebot.networks.network.hosts');
            });
            Route::get('/vulnerabilities', function() {
                return view('rebot.networks.network.vulnerabilities');
            });
        });
    });

    Route::get('cve/item', function () {
        return view('agbot.cve.item');
    });
    Route::get('cve/list', function () {
        return view('agbot.cve.list');
    });

});

/**
 * This part of router is for getting basic router
 */
Route::get('/{catchall?}', function () {
    return view('agbot.layouts.app');
});