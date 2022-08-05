package io.prophecy.pipelines.generaltests.graph

import io.prophecy.libs._
import io.prophecy.pipelines.generaltests.config.ConfigStore._
import io.prophecy.pipelines.generaltests.udfs.UDFs._
import io.prophecy.pipelines.generaltests.udfs._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object CreateExamples {

  def apply(spark: SparkSession, in: DataFrame): DataFrame =
    in.select(
      concat(lit("   "), col("c_name"), lit(" ")).as("extra_whitespace"),
      col("c_name"),
      upper(col("c_name")).as("all_upper"),
      lower(col("c_name")).as("all_lower"),
      concat(lit("----"), col("c_name"), lit("---")).as("extra_chars"),
      expr("if((rand() > 0.5D), 1, NULL)").as("sometimes_null"),
      expr("if((rand() > 0.5D), 'abc', '')").as("sometimes_empty"),
      date_format(current_date(), "yyyyMMdd").as("date_example")
    )

}
