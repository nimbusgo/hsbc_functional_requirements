package io.prophecy.pipelines.sqloperators.graph

import io.prophecy.libs._
import io.prophecy.pipelines.sqloperators.config.ConfigStore._
import io.prophecy.pipelines.sqloperators.udfs.UDFs._
import io.prophecy.pipelines.sqloperators.udfs._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object WindowFunction {

  def apply(spark: SparkSession, in: DataFrame): DataFrame = {
    import org.apache.spark.sql.expressions.{Window, WindowSpec}
    in.withColumn(
      "row_num",
      row_number().over(
        Window.partitionBy(col("c_nationkey")).orderBy(col("c_custkey").asc)
      )
    )
  }

}
