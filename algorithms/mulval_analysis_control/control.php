<?php
$url = 'http://localhost:8000/api/algorithms/1/results';
$html = file_get_contents($url);  
echo $html;  
?>