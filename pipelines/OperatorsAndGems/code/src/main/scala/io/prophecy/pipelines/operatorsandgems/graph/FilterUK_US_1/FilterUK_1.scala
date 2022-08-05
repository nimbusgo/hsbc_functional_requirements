package io.prophecy.pipelines.operatorsandgems.graph.FilterUK_US_1

import io.prophecy.libs._
import io.prophecy.pipelines.operatorsandgems.config.ConfigStore._
import io.prophecy.pipelines.operatorsandgems.udfs.UDFs._
import io.prophecy.pipelines.operatorsandgems.udfs._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object FilterUK_1 {

  def apply(spark: SparkSession, in: DataFrame): DataFrame =
    in.filter(col("nation_name") === lit("UNITED KINGDOM"))

}
