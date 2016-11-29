<?php

function rmdir_recursive($dir) {
    echo $dir . PHP_EOL;
    foreach(scandir($dir) as $file) {
        echo $file . PHP_EOL;
        if ('.' === $file || '..' === $file) continue;
        if (is_dir("$dir/$file")) rmdir_recursive("$dir/$file");
        else unlink("$dir/$file");
    }
    rmdir($dir);
}