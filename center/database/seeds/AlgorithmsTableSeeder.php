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
            'control_image' => '162.105.30.65:9998/serc/agbot:mulval',
            'created_at' => TimeUtility::currentTimeStamp(),
            'updated_at' => TimeUtility::currentTimeStamp()
        ]);
        DB::table('algorithms')->insert([
            'name' => "gen1",
            'type' => 0,
            'image' => 'gen1',
            'control_image' => 'gen1_control',
            'created_at' => TimeUtility::currentTimeStamp(),
            'updated_at' => TimeUtility::currentTimeStamp()
        ]);
        DB::table('algorithms')->insert([
            'name' => "MulVAL Analysis",
            'type' => 1,
            'image' => '162.105.30.65:9998/mulval_analysis',
            'control_image' => '162.105.30.65:9998/mulval_analysis_control',
            'created_at' => TimeUtility::currentTimeStamp(),
            'updated_at' => TimeUtility::currentTimeStamp()
        ]);
        DB::table('algorithms')->insert([
            'name' => "analysis1",
            'type' => 1,
            'image' => 'analysis1',
            'control_image' => 'analysis1_control',
            'created_at' => TimeUtility::currentTimeStamp(),
            'updated_at' => TimeUtility::currentTimeStamp()
        ]);
    }
}
