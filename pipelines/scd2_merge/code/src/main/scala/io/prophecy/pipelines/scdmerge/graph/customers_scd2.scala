package io.prophecy.pipelines.scdmerge.graph

import io.prophecy.libs._
import io.prophecy.pipelines.scdmerge.config.ConfigStore._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object customers_scd2 {

  def apply(spark: SparkSession): DataFrame =
    spark.read
      .format("delta")
      .load("/data/tmp/hsbc/tpch-examples/scd2_customers")

}
