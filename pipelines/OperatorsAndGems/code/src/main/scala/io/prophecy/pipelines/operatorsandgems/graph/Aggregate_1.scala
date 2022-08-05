package io.prophecy.pipelines.operatorsandgems.graph

import io.prophecy.libs._
import io.prophecy.pipelines.operatorsandgems.config.ConfigStore._
import io.prophecy.pipelines.operatorsandgems.udfs.UDFs._
import io.prophecy.pipelines.operatorsandgems.udfs._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object Aggregate_1 {

  def apply(spark: SparkSession, in: DataFrame): DataFrame =
    in.groupBy(col("p_brand"))
      .agg(
        min(col("p_retailprice")).as("min_price"),
        max(col("p_retailprice")).as("max_price"),
        count(col("p_partkey")).as("num_parts"),
        avg(col("p_retailprice")).as("avg_price"),
        sum(col("p_retailprice")).as("price_sum")
      )

}
