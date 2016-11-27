<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateAlgorithmTasksTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('algorithm_tasks', function (Blueprint $table) {
            $table->increments('id');
            $table->integer('gen_algorithm_id');
            $table->integer('gen_algorithm_result_id');
            $table->integer('ana_algorithm_id');
            $table->integer('ana_algorithm_result_id')->nullable()->default(null);
            $table->string('docker_container_id')->nullable()->default(null);
            $table->integer('status'); // 0-running 1-done
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
        Schema::dropIfExists('algorithm_tasks');
    }
}
