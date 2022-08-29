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

object updated {

  def apply(spark: SparkSession, in: DataFrame): DataFrame =
    in.filter(
      !(col("tax_id") <=> col("source_tax_id"))
        .and(col("tax_code") <=> col("source_tax_code"))
        .and(
          (col("customer_name") <=> col("source_customer_name"))
            .and(col("state") <=> col("source_state"))
        )
    )

}
