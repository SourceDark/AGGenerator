<?php

namespace App\Http\Utility;

class DockerUtility
{
    /**
     * Create container
     */
    static public function createAlgorithmContainer($image_name, $work_folder)
    {
        list($returnCode, $returnContent) = NetworkUtility::http_post_json(EnvUtility::C_DOCKER_API() . "/containers/create", json_encode(array(
            'Image' => $image_name,
            'HostConfig' => array(
                'Binds' => [$work_folder . ':/data']
            )
        )));
        if ($returnCode != 201) {
            echo "When creating containers:";
            var_dump($returnCode);
            var_dump($returnContent);
            return null;
        }
        $result = json_decode($returnContent);
        $container_id = $result->Id;
        return $container_id;
    }

    /**
     * Start container
     */
    static public function startAlgorithmContainer($container_id) {
        list($returnCode, $returnContent) = NetworkUtility::http_post_json(EnvUtility::C_DOCKER_API() . "/containers/" . $container_id . "/start");
        if ($returnCode != 204) {
            echo "When starting containers:";
            var_dump($returnCode);
            var_dump($returnContent);
            return null;
        }
    }
}
