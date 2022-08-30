package io.prophecy.pipelines.operatorsandgems.graph

import io.prophecy.libs._
import io.prophecy.pipelines.operatorsandgems.config.ConfigStore._
import io.prophecy.pipelines.operatorsandgems.udfs.UDFs._
import io.prophecy.pipelines.operatorsandgems.udfs._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import java.time._

object AggByCustomer {

  def apply(spark: SparkSession, in: DataFrame): DataFrame =
    in.groupBy(col("o_custkey"))
      .agg(count(col("o_orderkey")).as("num_orders"),
           sum(col("o_totalprice")).as("total_price")
      )

}
