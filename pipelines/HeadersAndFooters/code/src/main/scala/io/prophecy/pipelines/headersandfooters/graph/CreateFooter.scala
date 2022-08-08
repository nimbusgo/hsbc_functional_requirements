package io.prophecy.pipelines.headersandfooters.graph

import io.prophecy.libs._
import io.prophecy.pipelines.headersandfooters.config.ConfigStore._
import io.prophecy.pipelines.headersandfooters.udfs.UDFs._
import io.prophecy.pipelines.headersandfooters.udfs._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object CreateFooter {
  def apply(spark: SparkSession): DataFrame = {
    import spark.implicits._
    
    val df = List(1).toDF("value")
    
    val out0 = df.select(lit("footer, saved 1234 rows").as("value"))
    out0
  }

}
