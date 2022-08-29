package io.prophecy.pipelines.parquetscd.graph.GenerateRandomIncrements_0

import io.prophecy.libs._
import io.prophecy.pipelines.parquetscd.config.ConfigStore._
import io.prophecy.pipelines.parquetscd.udfs.UDFs._
import io.prophecy.pipelines.parquetscd.udfs._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import java.time._

object shift_ids {

  def apply(spark: SparkSession, in: DataFrame): DataFrame =
    in.withColumn("customer_id",
                  col("customer_id") + (lit(10000) * rand()).cast(IntegerType)
    )

}
