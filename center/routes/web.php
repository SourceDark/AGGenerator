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

/**
 * This part of router is for providing html fragments
 */
Route::group(['prefix' => 'html'], function () {
    Route::get('aboutUs', function ()    {
        return view('agbot.aboutUs.index');
    });
    Route::get('vision', function () {
        return view('agbot.vision.index');
    });
    Route::get('sensor', function () {
        return view('agbot.sensor.index');
    });
    Route::get('attackGraph', function () {
        return view('agbot.attackGraph.index');
    });
    Route::get('algorithms', function () {
        return view('agbot.algorithm.index');
    });
});

/**
 * This part of router is for getting basic router
 */
Route::get('/{catchall?}', function () {
    return view('agbot.layouts.app');
});