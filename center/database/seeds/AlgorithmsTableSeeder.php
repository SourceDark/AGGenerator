<?php

use Illuminate\Database\Seeder;
use app\Http\Utility\TimeUtility;

class AlgorithmsTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::table('algorithms')->insert([
            'name' => "MulVAL",
            'type' => 0,
            'control-image' => 'mulval',
            'created_at' => TimeUtility::currentTimeStamp(),
            'updated_at' => TimeUtility::currentTimeStamp()
        ]);
        DB::table('algorithms')->insert([
            'name' => "gen1",
            'type' => 0,
            'control-image' => 'gen1',
            'created_at' => TimeUtility::currentTimeStamp(),
            'updated_at' => TimeUtility::currentTimeStamp()
        ]);
        DB::table('algorithms')->insert([
            'name' => "MulVAL Analysis",
            'type' => 1,
            'control-image' => 'mulval-analysis',
            'created_at' => TimeUtility::currentTimeStamp(),
            'updated_at' => TimeUtility::currentTimeStamp()
        ]);
        DB::table('algorithms')->insert([
            'name' => "analysis1",
            'type' => 1,
            'control-image' => 'analysis1',
            'created_at' => TimeUtility::currentTimeStamp(),
            'updated_at' => TimeUtility::currentTimeStamp()
        ]);
    }
}
