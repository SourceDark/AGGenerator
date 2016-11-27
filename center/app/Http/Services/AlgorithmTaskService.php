<?php

namespace App\Http\Services;

use Illuminate\Support\Facades\DB;
use App\Http\Utility\TimeUtility;

class AlgorithmTaskService
{
    static public function createTaskByGenAndAna($gen_algorithm_id, $gen_algorithm_result_id, $ana_algorithm_id)
    {
        $algorithm_task_id = DB::table('algorithm_tasks')->insertGetId(array(
            'gen_algorithm_id' => $gen_algorithm_id,
            'gen_algorithm_result_id' => $gen_algorithm_result_id,
            'ana_algorithm_id' => $ana_algorithm_id,
            'status' => 0,
            'created_at' => TimeUtility::currentTimeStamp(),
            'updated_at' => TimeUtility::currentTimeStamp()
        ));
        return $algorithm_task_id;
    }

    static public function getTasksByGen($gen_algorithm_id, $gen_algorithm_result_id)
    {
        $algorithm_tasks = DB::table('algorithm_tasks')->where('gen_algorithm_id', $gen_algorithm_id)->where('gen_algorithm_result_id', $gen_algorithm_result_id)->get();
        return $algorithm_tasks;
    }
}
