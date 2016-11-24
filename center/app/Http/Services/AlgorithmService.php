<?php

namespace App\Http\Services;

use Illuminate\Support\Facades\DB;
use App\Http\Utility\TimeUtility;

class AlgorithmService
{
    static public function getGenerationAlgorithms()
    {
        $algorithms = DB::table('algorithms')->where('type', 0)->get();
        return $algorithms;
    }

    static public function getAnalysisAlgorithms()
    {
        $algorithms = DB::table('algorithms')->where('type', 1)->get();
        return $algorithms;
    }

    static public function getAlgorithmById($algorithm_id) {
        $algorithm = DB::table('algorithms')->where('id', $algorithm_id)->first();
        return $algorithm;
    }

    static public function getResultsByAlgorithmId($algorithm_id) {
        $algorithm_results = DB::table('algorithm_results')->where('algorithm_id', $algorithm_id)->get();
        return $algorithm_results;
    }

    static public function createResultsByAlgorithmIdAndContent($algorithm_id, $content) {
        $algorithm_result_id = DB::table('algorithm_results')->insertGetId(array(
            "algorithm_id" => $algorithm_id,
            "content" => $content,
            'created_at' => TimeUtility::currentTimeStamp(),
            'updated_at' => TimeUtility::currentTimeStamp()
        ));
        return $algorithm_result_id;
    }
}
