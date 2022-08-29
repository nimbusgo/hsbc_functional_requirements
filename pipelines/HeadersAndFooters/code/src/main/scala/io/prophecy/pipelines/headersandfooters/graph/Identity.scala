package io.prophecy.pipelines.headersandfooters.graph

import io.prophecy.libs._
import io.prophecy.pipelines.headersandfooters.config.ConfigStore._
import io.prophecy.pipelines.headersandfooters.udfs.UDFs._
import io.prophecy.pipelines.headersandfooters.udfs._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import java.time._

object Identity {

  def apply(spark: SparkSession, in: DataFrame): DataFrame =
    in.filter(lit(true))

}
