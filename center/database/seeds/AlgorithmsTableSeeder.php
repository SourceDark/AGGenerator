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
            'image' => 'mulval',
            'control-image' => 'mulval_control',
            'created_at' => TimeUtility::currentTimeStamp(),
            'updated_at' => TimeUtility::currentTimeStamp()
        ]);
        DB::table('algorithms')->insert([
            'name' => "gen1",
            'type' => 0,
            'image' => 'gen1',
            'control-image' => 'gen1_control',
            'created_at' => TimeUtility::currentTimeStamp(),
            'updated_at' => TimeUtility::currentTimeStamp()
        ]);
        DB::table('algorithms')->insert([
            'name' => "MulVAL Analysis",
            'type' => 1,
            'image' => 'agbot/mulval_analysis',
            'control-image' => 'agbot/mulval_analysis_control',
            'created_at' => TimeUtility::currentTimeStamp(),
            'updated_at' => TimeUtility::currentTimeStamp()
        ]);
        DB::table('algorithms')->insert([
            'name' => "analysis1",
            'type' => 1,
            'image' => 'analysis1',
            'control-image' => 'analysis1_control',
            'created_at' => TimeUtility::currentTimeStamp(),
            'updated_at' => TimeUtility::currentTimeStamp()
        ]);
    }
}
