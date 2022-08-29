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

object HandleUpdates {

  def apply(
    spark:     SparkSession,
    increment: DataFrame,
    current:   DataFrame
  ): DataFrame =
    increment
      .as("increment")
      .join(current.as("current"),
            col("current.customer_id") === col("increment.customer_id"),
            "outer"
      )
      .select(
        col("current.customer_id").as("customer_id"),
        col("current.tax_id").as("tax_id"),
        col("current.tax_code").as("tax_code"),
        col("current.customer_name").as("customer_name"),
        col("current.state").as("state"),
        col("current.from_time").as("from_time"),
        col("current.end_time").as("end_time"),
        col("current.is_current").as("is_current"),
        col("current.is_old_value").as("is_old_value"),
        col("increment.customer_id").as("source_customer_id"),
        col("increment.tax_id").as("source_tax_id"),
        col("increment.tax_code").as("source_tax_code"),
        col("increment.customer_name").as("source_customer_name"),
        col("increment.state").as("source_state"),
        col("increment.from_time").as("source_from_time"),
        col("increment.end_time").as("source_end_time"),
        col("increment.is_current").as("source_is_current"),
        col("increment.is_old_value").as("source_is_old_value")
      )

}
