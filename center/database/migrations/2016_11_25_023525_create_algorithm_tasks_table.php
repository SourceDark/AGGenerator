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
            $table->integer('algorithm_id');
            $table->string('input');
            $table->integer('result_id')->nullable()->default(null);
            $table->string('container_id')->nullable()->default(null);
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
