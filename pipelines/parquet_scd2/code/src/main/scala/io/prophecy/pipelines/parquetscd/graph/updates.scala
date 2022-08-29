package io.prophecy.pipelines.parquetscd.graph

import io.prophecy.libs._
import io.prophecy.pipelines.parquetscd.config.ConfigStore._
import io.prophecy.pipelines.parquetscd.udfs.UDFs._
import io.prophecy.pipelines.parquetscd.udfs._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import java.time._

object updates {

  def apply(spark: SparkSession, in: DataFrame): DataFrame =
    in.select(
      col("source_customer_id").as("customer_id"),
      col("source_tax_id").as("tax_id"),
      col("source_tax_code").as("tax_code"),
      col("source_customer_name").as("customer_name"),
      col("source_state").as("state"),
      col("source_from_time").as("from_time"),
      col("source_end_time").as("end_time"),
      col("source_is_current").as("is_current"),
      lit(false).as("is_old_value")
    )

}
