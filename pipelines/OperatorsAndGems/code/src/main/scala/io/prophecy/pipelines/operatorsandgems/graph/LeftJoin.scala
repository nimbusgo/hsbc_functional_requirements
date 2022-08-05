package io.prophecy.pipelines.operatorsandgems.graph

import io.prophecy.libs._
import io.prophecy.pipelines.operatorsandgems.config.ConfigStore._
import io.prophecy.pipelines.operatorsandgems.udfs.UDFs._
import io.prophecy.pipelines.operatorsandgems.udfs._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object LeftJoin {

  def apply(spark: SparkSession, in0: DataFrame, in1: DataFrame): DataFrame =
    in0
      .as("in0")
      .join(in1.as("in1"),
            col("in0.c_custkey") === col("in1.o_custkey"),
            "left_outer"
      )
      .select(
        col("in0.c_custkey").as("c_custkey"),
        col("in0.c_name").as("c_name"),
        col("in0.c_address").as("c_address"),
        col("in0.c_nationkey").as("c_nationkey"),
        col("in0.c_phone").as("c_phone"),
        col("in0.c_acctbal").as("c_acctbal"),
        col("in0.c_mktsegment").as("c_mktsegment"),
        col("in0.c_comment").as("c_comment"),
        col("in0.nation_name").as("nation_name"),
        col("in1.o_custkey").as("o_custkey"),
        col("in1.num_orders").as("num_orders"),
        col("in1.total_price").as("total_price")
      )

}
