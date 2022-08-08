package io.prophecy.pipelines.scdmerge.graph.GenerateRandomIncrements_0

import io.prophecy.libs._
import io.prophecy.pipelines.scdmerge.config.ConfigStore._
import io.prophecy.pipelines.scdmerge.udfs.UDFs._
import io.prophecy.pipelines.scdmerge.udfs._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object Union {

  def apply(
    spark: SparkSession,
    in0:   DataFrame,
    in1:   DataFrame,
    in2:   DataFrame
  ): DataFrame = in0.unionAll(in1).unionAll(in2)

}
