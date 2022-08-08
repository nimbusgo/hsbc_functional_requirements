package io.prophecy.pipelines.headersandfooters.graph

import io.prophecy.libs._
import io.prophecy.pipelines.headersandfooters.config.ConfigStore._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object customers_text {

  def apply(spark: SparkSession): DataFrame =
    spark.read
      .format("text")
      .schema(StructType(Array(StructField("value", StringType, true))))
      .load("dbfs:/databricks-datasets/retail-org/customers/customers.csv")

}
