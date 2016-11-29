<?php

namespace App\Http\Services;

use Illuminate\Support\Facades\DB;
use App\Http\Utility\TimeUtility;

class AlgorithmTaskService
{
    static public function createTaskByAlgorithmId($algorithm_id)
    {
        $algorithm_task_id = DB::table('algorithm_tasks')->insertGetId(array(
            'algorithm_id' => $algorithm_id->id,
            'status' => 0,
            'created_at' => TimeUtility::currentTimeStamp(),
            'updated_at' => TimeUtility::currentTimeStamp()
        ));
        return $algorithm_task_id;
    }

    static public function updateTaskByIdAndParams($task_id, $params)
    {
        // TODO - verify param
        DB::table('algorithm_tasks')->where('id', $task_id)->update($params);
    }
}
