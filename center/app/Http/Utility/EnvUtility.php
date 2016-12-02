<?php

namespace App\Http\Utility;

class EnvUtility
{
    static public function C_CENTER_API()
    {
        return env('C_CENTER_API');
    }

    static public function C_DOCKER_API()
    {
        return env('C_DOCKER_API');
    }

    static public function C_STORAGE()
    {
        return env('C_STORAGE');
    }

    static public function BASE_STORAGE()
    {
        return env('BASE_STORAGE');
    }
    static public function CVE_API() {
        return env('CVE_API');
    }
}