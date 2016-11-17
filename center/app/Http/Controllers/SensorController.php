<?php

namespace App\Http\Controllers;

use Illuminate\Support\Facades\DB;
use Illuminate\Routing\Controller as BaseController;

class SensorController extends BaseController
{
    static public function getSensorByName($sensor_name) {
        $sensor = DB::table('sensors')->where('name', $sensor_name)->first();
        return $sensor;
    }
}
