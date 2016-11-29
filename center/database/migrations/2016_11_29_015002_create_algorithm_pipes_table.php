<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateAlgorithmPipesTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('algorithm_pipes', function (Blueprint $table) {
            $table->increments('id');
            $table->integer('algorithm1_id');
            $table->integer('algorithm1_result_id');
            $table->integer('algorithm2_id');
            $table->integer('algorithm2_task_id');
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('algorithm_pipes');
    }
}
