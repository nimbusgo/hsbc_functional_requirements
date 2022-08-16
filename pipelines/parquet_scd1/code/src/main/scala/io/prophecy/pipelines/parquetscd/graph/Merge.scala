package io.prophecy.pipelines.parquetscd.graph

import io.prophecy.libs._
import io.prophecy.pipelines.parquetscd.config.ConfigStore._
import io.prophecy.pipelines.parquetscd.udfs.UDFs._
import io.prophecy.pipelines.parquetscd.udfs._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object Merge {

  def apply(
    spark:         SparkSession,
    new_increment: DataFrame,
    existing:      DataFrame
  ): DataFrame =
    new_increment
      .as("new_increment")
      .join(existing.as("existing"),
            col("existing.customer_id") === col("new_increment.customer_id"),
            "outer"
      )
      .select(
        coalesce(col("new_increment.customer_id"), col("existing.customer_id"))
          .as("customer_id"),
        expr(
          "if(isnotnull(new_increment.customer_id), new_increment.tax_id, existing.tax_id)"
        ).as("tax_id"),
        expr(
          "if(isnotnull(new_increment.customer_id), new_increment.tax_code, existing.tax_code)"
        ).as("tax_code"),
        expr(
          "if(isnotnull(new_increment.customer_id), new_increment.customer_name, existing.customer_name)"
        ).as("customer_name"),
        expr(
          "if(isnotnull(new_increment.customer_id), new_increment.state, existing.state)"
        ).as("state")
      )

}
