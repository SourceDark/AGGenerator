<?php

use Illuminate\Database\Seeder;

class DatabaseSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        $this->call(SensorsTableSeeder::class);
        $this->call(AlgorithmsTableSeeder::class);
        $this->call(AlgorithmResultsTableSeeder::class);
    }
}
