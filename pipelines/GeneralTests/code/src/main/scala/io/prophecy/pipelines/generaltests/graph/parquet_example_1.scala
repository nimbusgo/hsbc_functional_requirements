package io.prophecy.pipelines.generaltests.graph

import io.prophecy.libs._
import io.prophecy.pipelines.generaltests.config.ConfigStore._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object parquet_example_1 {

  def apply(spark: SparkSession): DataFrame =
    spark.read
      .format("parquet")
      .load("/data/tmp/hsbc/tpch-examples/parquet-example")

}
