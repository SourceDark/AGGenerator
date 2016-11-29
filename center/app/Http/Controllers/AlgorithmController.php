<?php

namespace App\Http\Controllers;

use App\Http\Utility\DockerUtility;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\Http\Services\AlgorithmService;
use App\Http\Services\AlgorithmTaskService;
use App\Http\Utility\EnvUtility;
use App\Http\Utility\FileUtility;

class AlgorithmController extends Controller
{
    public function createAnalysisByGenerationResult(Request $request, $algorithm_id, $result_id)
    {
        // Get analysis algorithm
        $analysis_algorithm_id = $request->input("analysis_algorithm_id");
        $analysis_algorithm = AlgorithmService::getAlgorithmById($analysis_algorithm_id);

        // Create input file content for control
        $control_input = array(
            'center_api' => EnvUtility::C_CENTER_API(),
            'docker_api' => EnvUtility::C_DOCKER_API(),
            'algorithm_id' => $analysis_algorithm_id,
            'algorithm_image' => $analysis_algorithm->image,
            'base_folder' => EnvUtility::C_STORAGE(),
            'input' => array(
                'algorithm_id' => $algorithm_id,
                'result_id' => $result_id
            )
        );

        // Create Task
        $algorithm_task_id = AlgorithmTaskService::createTaskByAlgorithmAndInput($analysis_algorithm, json_encode($control_input));

        // Write Input file
        FileUtility::writeJson2File($control_input, $analysis_algorithm->name, stringValue($algorithm_task_id));

        // Create Container
        $container_id = DockerUtility::runAlgorithmImage($analysis_algorithm->control_image, EnvUtility::BASE_STORAGE() . '/' . $algorithm_task_id);

        // Create Task





        /**
         * Create new task
         */
        // TODO validate $algorithm to be generation


        //$algorithm_task_id = AlgorithmTaskService::createTaskByGenAndAna($algorithm_id, $result_id, $analysis_algorithm_id);

        return response()->json($container_id, 201);
    }
}