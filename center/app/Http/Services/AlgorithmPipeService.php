<?php

namespace App\Http\Services;

use Illuminate\Support\Facades\DB;
use App\Http\Utility\TimeUtility;

class AlgorithmPipeService
{
    static public function createByAlgorithms($algorithm1_id, $algorithm1_result_id, $algorithm2_id, $algorithm2_task_id)
    {
        $algorithm_task_id = DB::table('algorithm_pipes')->insertGetId(array(
            'algorithm1_id' => $algorithm1_id,
            'algorithm1_result_id' => $algorithm1_result_id,
            'algorithm2_id' => $algorithm2_id,
            'algorithm2_task_id' => $algorithm2_task_id,
            'created_at' => TimeUtility::currentTimeStamp(),
            'updated_at' => TimeUtility::currentTimeStamp()
        ));
        return $algorithm_task_id;
    }

    static public function getByAlgorithm1Id($algorithm1_id)
    {
        $algorithm_pipes = DB::table('algorithm_pipes')->where('algorithm1_id', $algorithm1_id)->get();
        return $algorithm_pipes;
    }
}
