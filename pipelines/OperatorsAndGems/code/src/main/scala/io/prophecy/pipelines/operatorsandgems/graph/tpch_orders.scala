package io.prophecy.pipelines.operatorsandgems.graph

import io.prophecy.libs._
import io.prophecy.pipelines.operatorsandgems.config.ConfigStore._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object tpch_orders {

  def apply(spark: SparkSession): DataFrame =
    spark.read
      .format("delta")
      .load("dbfs:/databricks-datasets/tpch/delta-001/orders/")

}
