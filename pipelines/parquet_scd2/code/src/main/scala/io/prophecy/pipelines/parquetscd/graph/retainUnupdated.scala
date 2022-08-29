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

object retainUnupdated {

  def apply(spark: SparkSession, in: DataFrame): DataFrame =
    in.select(
      col("customer_id"),
      col("tax_id"),
      col("tax_code"),
      col("customer_name"),
      col("state"),
      col("from_time"),
      col("end_time"),
      col("is_current"),
      col("is_old_value")
    )

}
