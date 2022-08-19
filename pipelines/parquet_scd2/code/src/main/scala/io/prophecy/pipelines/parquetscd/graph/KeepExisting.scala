package io.prophecy.pipelines.parquetscd.graph

import io.prophecy.libs._
import io.prophecy.pipelines.parquetscd.config.ConfigStore._
import io.prophecy.pipelines.parquetscd.udfs.UDFs._
import io.prophecy.pipelines.parquetscd.udfs._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object KeepExisting {

  def apply(
    spark:     SparkSession,
    current:   DataFrame,
    increment: DataFrame
  ): DataFrame =
    current
      .as("current")
      .join(increment.as("increment"),
            col("current.customer_id") === col("increment.customer_id"),
            "left_anti"
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
        col("current.is_old_value").as("is_old_value")
      )

}
