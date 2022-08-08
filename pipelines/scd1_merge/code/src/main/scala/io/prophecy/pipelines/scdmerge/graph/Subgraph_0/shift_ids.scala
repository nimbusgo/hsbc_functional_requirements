package io.prophecy.pipelines.scdmerge.graph.Subgraph_0

import io.prophecy.libs._
import io.prophecy.pipelines.scdmerge.config.ConfigStore._
import io.prophecy.pipelines.scdmerge.udfs.UDFs._
import io.prophecy.pipelines.scdmerge.udfs._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object shift_ids {

  def apply(spark: SparkSession, in: DataFrame): DataFrame =
    in.withColumn("customer_id",
                  col("customer_id") + (lit(10000) * rand()).cast(IntegerType)
    )

}
