package io.prophecy.pipelines.generaltests.graph

import io.prophecy.libs._
import io.prophecy.pipelines.generaltests.config.ConfigStore._
import io.prophecy.pipelines.generaltests.udfs.UDFs._
import io.prophecy.pipelines.generaltests.udfs._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object SchemaTransform_1 {

  def apply(spark: SparkSession, in: DataFrame): DataFrame =
    in.withColumn("c_name",
                  when(lit(Config.flag) === lit("upper"), upper(col("c_name")))
                    .otherwise(col("c_name"))
    )

}
