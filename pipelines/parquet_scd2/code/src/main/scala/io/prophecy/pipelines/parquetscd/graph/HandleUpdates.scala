package io.prophecy.pipelines.parquetscd.graph

import io.prophecy.libs._
import io.prophecy.pipelines.parquetscd.config.ConfigStore._
import io.prophecy.pipelines.parquetscd.udfs.UDFs._
import io.prophecy.pipelines.parquetscd.udfs._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

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
            "inner"
      )
      .where(
        !(col("current.tax_id") <=> col("increment.tax_id"))
          .and(col("current.tax_code") <=> col("increment.tax_code"))
          .and(
            (col("current.customer_name") <=> col("increment.customer_name"))
              .and(col("current.state") <=> col("increment.state"))
          )
      )
      .select(
        col("current.customer_id").as("customer_id"),
        col("current.tax_id").as("tax_id"),
        col("current.tax_code").as("tax_code"),
        col("current.customer_name").as("customer_name"),
        col("current.state").as("state"),
        col("current.from_time").as("from_time"),
        col("increment.from_time").as("end_time"),
        lit(false).as("is_current"),
        lit(true).as("is_old_value")
      )

}
