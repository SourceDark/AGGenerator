<?php

namespace App\Http\Utility;

class TimeUtility
{
    static public function currentTimeStamp() {
        return date('Y-m-d H:i:s');
    }
}