<?php

namespace App\Http\Utility;

class TimeUtility
{
    static public function currentTimeStamp() {
        date_default_timezone_set("Asia/Shanghai");
        return date('Y-m-d H:i:s');
    }
}