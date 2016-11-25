<?php

namespace App\Http\Utility;

class EnvUtility
{
    static public function C_Host() {
        return env('C_HOST');
    }

    static public function C_STORAGE() {
        return env('C_STORAGE');
    }
}