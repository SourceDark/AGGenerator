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
}
