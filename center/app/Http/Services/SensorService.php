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

    static public function getSensors() {
        $sensors = DB::select('select * from sensors');
        foreach ($sensors as $sensor) {
            $sensor -> hosts = DB::select('select * from vul_reports where sensor_id = ?', array($sensor -> id));
            foreach ($sensor -> hosts as $host) {
                $host -> reports = DB::select('select * from vul_report_records where vul_report_id = ?', array($host -> id));
            }
        }
        return $sensors;
    }
}
