package io.prophecy.pipelines.headersandfooters.graph

import io.prophecy.libs._
import io.prophecy.pipelines.headersandfooters.config.ConfigStore._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object concatenated_output_1 {

  def apply(spark: SparkSession, in: DataFrame): Unit = {
    var writer = in.write.format("text").mode("overwrite")
    writer = writer
    writer = writer
    writer.save("/data/tmp/hsbc/tpch-examples/header_footers/concatenated")
  }

}
