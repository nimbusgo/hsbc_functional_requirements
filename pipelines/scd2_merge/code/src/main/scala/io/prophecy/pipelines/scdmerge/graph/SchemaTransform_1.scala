package io.prophecy.pipelines.scdmerge.graph

import io.prophecy.libs._
import io.prophecy.pipelines.scdmerge.config.ConfigStore._
import io.prophecy.pipelines.scdmerge.udfs.UDFs._
import io.prophecy.pipelines.scdmerge.udfs._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object SchemaTransform_1 {

  def apply(spark: SparkSession, in: DataFrame): DataFrame =
    in.withColumn("from_time",    current_timestamp())
      .withColumn("end_time",     lit(null).cast(TimestampType))
      .withColumn("is_current",   lit(true))
      .withColumn("is_old_value", lit(false))

}
