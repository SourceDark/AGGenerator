<?php

namespace App\Http\Services;

use Illuminate\Support\Facades\DB;
use App\Http\Utility\TimeUtility;

class AlgorithmTaskService
{
    static public function createTaskByAlgorithmAndInput($algorithm, $input)
    {
        $algorithm_task_id = DB::table('algorithm_tasks')->insertGetId(array(
            'algorithm_id' => $algorithm->id,
            'input' => $input,
            'status' => 0,
            'created_at' => TimeUtility::currentTimeStamp(),
            'updated_at' => TimeUtility::currentTimeStamp()
        ));
        return $algorithm_task_id;
    }
}
