package io.prophecy.pipelines.sqloperators.graph

import io.prophecy.libs._
import io.prophecy.pipelines.sqloperators.config.ConfigStore._
import io.prophecy.pipelines.sqloperators.udfs.UDFs._
import io.prophecy.pipelines.sqloperators.udfs._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object AggChecks {

  def apply(spark: SparkSession, in: DataFrame): DataFrame =
    in.select(max(col("c_acctbal")).as("max_balance"),
              countDistinct(col("c_nationkey")).as("num_nations"),
              sum(col("c_acctbal")).as("balance_sum")
    )

}
