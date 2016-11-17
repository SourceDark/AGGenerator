<?php

use Illuminate\Database\Seeder;
use App\Http\Utility\TimeUtility;

class SensorsTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::table('sensors')->insert([
            'name' => str_random(10),
            'ip' => '192.168.100.53',
            'created_at' => TimeUtility::currentTimeStamp(),
            'updated_at' => TimeUtility::currentTimeStamp()
        ]);
    }
}
