package io.prophecy.pipelines.operatorsandgems.graph

import io.prophecy.libs._
import io.prophecy.pipelines.operatorsandgems.config.ConfigStore._
import io.prophecy.pipelines.operatorsandgems.udfs.UDFs._
import io.prophecy.pipelines.operatorsandgems.udfs._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object WithNtnName {

  def apply(spark: SparkSession, in: DataFrame): DataFrame =
    in.withColumn("nation_name",
                  lookup("Nation", col("c_nationkey")).getField("n_name")
    )

}
