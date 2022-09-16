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

object FilterUK_US_1 {

  def apply(spark: SparkSession, in: DataFrame): (DataFrame, DataFrame) =
    (in.filter(col("nation_name") === lit("UNITED KINGDOM")),
     in.filter(col("nation_name") === lit("UNITED STATES"))
    )

}
