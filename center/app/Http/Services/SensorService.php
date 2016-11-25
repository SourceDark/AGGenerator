<?php

namespace App\Http\Services;

use Illuminate\Support\Facades\DB;
use App\Http\Utility\TimeUtility;

class SensorService
{
    static public function getSensorById($sensor_id)
    {
        $sensor = DB::table('sensors')->where('id', $sensor_id)->first();
        return $sensor;
    }

    static public function getSensorByName($sensor_name)
    {
        $sensor = DB::table('sensors')->where('name', $sensor_name)->first();
        return $sensor;
    }

    static public function createSensorByName($sensor_name)
    {
        $sensor_id = DB::table('sensors')->insertGetId([
            'name' => $sensor_name,
            'created_at' => TimeUtility::currentTimeStamp(),
            'updated_at' => TimeUtility::currentTimeStamp()
        ]);
        return self::getSensorById($sensor_id);
    }
}
