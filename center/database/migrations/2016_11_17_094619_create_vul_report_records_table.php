<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateVulReportRecordsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('vul_report_records', function (Blueprint $table) {
            $table->increments('id');
            $table->string('vul_report_id');
            $table->string('port_name');
            $table->string('port_proto');
            $table->string('threat');
            $table->string('cves');
            $table->string('description')->default("");
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
        Schema::dropIfExists('vul_report_records');
    }
}
