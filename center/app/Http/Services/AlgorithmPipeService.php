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

    static public function getTasksByAlgorithm1IdAndAlgorithm1ResultId($algorithm1_id, $algorithm1_result_id)
    {
        $algorithm_pipes = DB::table('algorithm_pipes')->where('algorithm1_id', $algorithm1_id)->where('algorithm1_result_id', $algorithm1_result_id)->get();
        return $algorithm_pipes;
    }

    static public function fillResultWithExportTasks($result)
    {
        $result->export_tasks = DB::table('algorithm_pipes')
            ->where('algorithm1_id', $result->algorithm_id)
            ->where('algorithm1_result_id', $result->id)
            ->leftJoin('algorithm_tasks', 'algorithm_pipes.algorithm2_task_id', '=', 'algorithm_tasks.id')
            ->select('algorithm_tasks.*')
            ->get();
    }

    static public function getById($pipe_id)
    {
        $algorithm_pipe = DB::table('algorithm_pipes')->where('id', $pipe_id)->get();
        return $algorithm_pipe;
    }
}
