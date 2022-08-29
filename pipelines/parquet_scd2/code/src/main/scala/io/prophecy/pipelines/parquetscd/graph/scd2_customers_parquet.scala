package io.prophecy.pipelines.parquetscd.graph

import io.prophecy.libs._
import io.prophecy.pipelines.parquetscd.config.ConfigStore._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import java.time._

object scd2_customers_parquet {

  def apply(spark: SparkSession, in: DataFrame): Unit =
    in.write
      .format("parquet")
      .mode("overwrite")
      .save("/data/tmp/hsbc/tpch-examples/scd2_customers_parquet")

}
