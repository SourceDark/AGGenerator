<?php
require_once("Network.php");

/**
 * Get configurations from file
 */
$input_file_content = file_get_contents("/data/input");
$inputs = json_decode($input_file_content);
$center_api = $inputs->center_api;
$docker_api = $inputs->docker_api;
$algorithm_id = $inputs->algorithm_id;
$algorithm_image = $inputs->algorithm_image;
$base_folder = $inputs->base_folder;
$gen_alg_params = $inputs->input;

/**
 * Loading results of generation
 */
$gen_alg_result_content = file_get_contents($center_api . "/api/algorithms/" . $gen_alg_params->algorithm_id . "/results/" . $gen_alg_params->result_id);
$gen_alg_result = json_decode($gen_alg_result_content);
$alg_inputs = json_decode($gen_alg_result->content);
$nodes = $alg_inputs->nodes;
$edges = $alg_inputs->edges;

/**
 * Write input file for algorithm
 */
if (!is_dir('/data/algorithm')) {
    mkdir('/data/algorithm');
}
$ouf = fopen('/data/algorithm/input', "w");
fwrite($ouf, sizeof($nodes) . ' ' . sizeof($edges) . PHP_EOL);
foreach ($nodes as $node) {
    fwrite($ouf, $node->id . PHP_EOL);
    fwrite($ouf, $node->info . PHP_EOL);
    fwrite($ouf, $node->type . PHP_EOL);
    fwrite($ouf, $node->initial . PHP_EOL);
}
foreach ($edges as $edge) {
    fwrite($ouf, $edge->target . PHP_EOL);
    fwrite($ouf, $edge->source . PHP_EOL);
}
fclose($ouf);

/**
 * Use docker remote api
 */


/**
 * Create container
 */
list($returnCode, $returnContent) = http_post_json($docker_api . "/containers/create", json_encode(array(
    'Image' => $algorithm_image,
    'HostConfig' => array(
        'Binds' => [$base_folder . '/algorithm:/data']
    )
)));
if ($returnCode != 201) {
    echo "When creating containers:";
    var_dump($returnCode);
    var_dump($returnContent);
    return;
}
$result = json_decode($returnContent);
$container_id = $result->Id;
echo $container_id . PHP_EOL;

/**
 * Start container
 */
list($returnCode, $returnContent) = http_post_json($docker_api . "/containers/" . $container_id . "/start");
if ($returnCode != 204) {
    echo "When starting containers:";
    var_dump($returnCode);
    var_dump($returnContent);
    return;
}

/**
 * Wait container
 */
list($returnCode, $returnContent) = http_post_json($docker_api . "/containers/" . $container_id . "/wait", json_encode(array()));
if ($returnCode != 200) {
    echo "When waiting containers:";
    var_dump($returnCode);
    var_dump($returnContent);
    return;
}

/**
 * Remove container
 */
list($returnCode, $returnContent) = http_delete_json($docker_api . "/containers/" . $container_id . "?v=1", json_encode(array()));
if ($returnCode != 204) {
    echo "When removing containers:";
    var_dump($returnCode);
    var_dump($returnContent);
    return;
}

/**
 * Decode outputs of algorithm
 */
$inf = fopen("/data/algorithm/output", "r");
$n = intval(fgets($inf));
$paths = array();
for ($i = 0; $i < $n; $i++) {
    $path = explode(',', fgets($inf));
    for ($j = 0; $j < sizeof($path); $j++) $path[$j] = intval($path[$j]);
    array_push($paths, $path);
}

/**
 * Post result to center server
 */
list($returnCode, $returnContent) = http_post_json($center_api . "/api/algorithms/" . $algorithm_id . "/results", json_encode(array(
    "content" => json_encode(array(
        "input" => array(
            "algorithm_id" => $gen_alg_params->algorithm_id,
            "result_id" => $gen_alg_params->result_id
        ),
        "PathList" => $paths
    ))
)));
if ($returnCode != 200) {
    echo "When pushing result:";
    var_dump($returnCode);
    var_dump($returnContent);
    return;
}

?>