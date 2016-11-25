<?php
/**
 * Get configurations from file
 */
$input_file_content = file_get_contents("/data/input");
$inputs = json_decode($input_file_content);
$host_api = $inputs->host_api;
$gen_alg_params = $inputs->input;

/**
 * Loading results of generation
 */
$gen_alg_result_content = file_get_contents("http://" . $host_api . "/api/algorithms/" . $gen_alg_params->algorithm_id . "/results/" . $gen_alg_params->result_id);
$gen_alg_result = json_decode($gen_alg_result_content);
$alg_inputs = json_decode($gen_alg_result->content);
$nodes = $alg_inputs->nodes;
$edges = $alg_inputs->edges;
var_dump($nodes);
var_dump($edges);

/**
 * Write input file for algorithm
 */
if (!is_dir('/data/algorithm')) {
    mkdir('/data/algorithm');
}
$ouf = fopen('/data/algorithm/input', "w");
fwrite($ouf, sizeof($nodes) . ' ' . sizeof($edges) . "\r\n");
foreach ($nodes as $node) {
    fwrite($ouf, $node->id . "\r\n");
    fwrite($ouf, $node->info . "\r\n");
    fwrite($ouf, $node->type . "\r\n");
    fwrite($ouf, $node->initial . "\r\n");
}
foreach ($edges as $edge) {
    fwrite($ouf, $edge->target . "\r\n");
    fwrite($ouf, $edge->source . "\r\n");
}
?>