package io.prophecy.pipelines.generaltests.graph

import io.prophecy.libs._
import io.prophecy.pipelines.generaltests.config.ConfigStore._
import io.prophecy.pipelines.generaltests.udfs.UDFs._
import io.prophecy.pipelines.generaltests.udfs._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object CreateLast {

  def apply(spark: SparkSession, in: DataFrame): DataFrame =
    in.withColumn("ordering", lit(2))

}
