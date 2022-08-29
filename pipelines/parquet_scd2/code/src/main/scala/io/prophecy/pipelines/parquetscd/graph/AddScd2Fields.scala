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

object AddScd2Fields {

  def apply(spark: SparkSession, in: DataFrame): DataFrame =
    in.withColumn("from_time",    current_timestamp())
      .withColumn("end_time",     lit(null).cast(TimestampType))
      .withColumn("is_current",   lit(true))
      .withColumn("is_old_value", lit(true))

}
