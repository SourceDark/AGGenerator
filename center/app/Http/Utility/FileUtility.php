<?php

namespace App\Http\Utility;

class FileUtility
{
    public static function remove_directory($dir)
    {
        if ($handle = opendir("$dir")) {
            while (false !== ($item = readdir($handle))) {
                if ($item != "." && $item != "..") {
                    if (is_dir("$dir/$item")) {
                        self::remove_directory("$dir/$item");
                    } else {
                        unlink("$dir/$item");
                    }
                }
            }
            closedir($handle);
            rmdir($dir);
        }
    }

    static public function writeJson2File($object, $path, $file)
    {
        if (file_exists(EnvUtility::BASE_STORAGE() . '/' . $path)) {
            self::remove_directory(EnvUtility::BASE_STORAGE() . '/' . $path);
        }
        mkdir(EnvUtility::BASE_STORAGE() . '/' . $path);
        file_put_contents(EnvUtility::BASE_STORAGE() . '/' . $path . '/' . $file, json_encode($object));
    }
}