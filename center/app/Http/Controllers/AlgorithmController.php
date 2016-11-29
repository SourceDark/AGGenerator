<?php

namespace App\Http\Controllers;

use App\Http\Services\AlgorithmPipeService;
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
        // TODO validate $algorithm to be generation

        // Get analysis algorithm
        $analysis_algorithm_id = $request->input("analysis_algorithm_id");
        if ($analysis_algorithm_id != null) $analysis_algorithm = AlgorithmService::getAlgorithmById($analysis_algorithm_id);
        if ($analysis_algorithm == null || $analysis_algorithm->type != 1) {
            return "analysis_algorithm_id doesn't refer to a analysis algorithm.";
        }

        // Create Task
        $algorithm_task_id = AlgorithmTaskService::createTaskByAlgorithmId($analysis_algorithm);

        // Create input file content for control
        $control_input = [
            'host' => [
                'center_api' => EnvUtility::C_CENTER_API(),
                'docker_api' => EnvUtility::C_DOCKER_API()
            ],
            'container' => [
                'base_folder' => EnvUtility::C_STORAGE() . '/' . $algorithm_task_id,
                'algorithm_task_id' => $algorithm_task_id
            ],
            'algorithm' => [
                'id' => $analysis_algorithm->id,
                'image' => $analysis_algorithm->image
            ],
            'input' => array(
                'algorithm_id' => $algorithm_id,
                'result_id' => $result_id
            )
        ];

        // Write Input file
        FileUtility::writeJson2File($control_input, (string)$algorithm_task_id, 'input');

        // Create Container
        $container_id = DockerUtility::createAlgorithmContainer($analysis_algorithm->control_image, EnvUtility::BASE_STORAGE() . '/' . $algorithm_task_id);

        // Update Task
        AlgorithmTaskService::updateTaskByIdAndParams($algorithm_task_id, [
            'input' => json_encode($control_input),
            'container_id' => $container_id
        ]);

        // Create Pipe
        $algorithm_pipe_id = AlgorithmPipeService::createByAlgorithms($algorithm_id, $result_id, $analysis_algorithm_id, $algorithm_task_id);
        $algorithm_pipe = AlgorithmPipeService::getById($algorithm_pipe_id);

        // Start Container
        DockerUtility::startAlgorithmContainer($container_id);

        return response()->json($algorithm_pipe, 201);
    }

    public function acceptAnalysisFromControl(Request $request, $algorithm_id, $task_id) {
        $result = $request->input("status");
        print_r($_POST);
        return $result. $algorithm_id . ',' . $task_id;
    }
}