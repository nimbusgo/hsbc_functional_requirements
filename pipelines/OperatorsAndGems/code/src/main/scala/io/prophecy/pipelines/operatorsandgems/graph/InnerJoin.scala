package io.prophecy.pipelines.operatorsandgems.graph

import io.prophecy.libs._
import io.prophecy.pipelines.operatorsandgems.config.ConfigStore._
import io.prophecy.pipelines.operatorsandgems.udfs.UDFs._
import io.prophecy.pipelines.operatorsandgems.udfs._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object InnerJoin {

  def apply(spark: SparkSession, in0: DataFrame, in1: DataFrame): DataFrame =
    in0
      .as("in0")
      .join(in1.as("in1"),
            col("in0.c_custkey") === col("in1.c_custkey"),
            "inner"
      )
      .select(
        col("in0.c_custkey").as("us_c_custkey"),
        col("in0.c_name").as("us_c_name"),
        col("in0.nation_name").as("us_nation_name"),
        col("in1.c_custkey").as("uk_c_custkey"),
        col("in1.c_name").as("uk_c_name"),
        col("in1.nation_name").as("uk_nation_name")
      )

}
