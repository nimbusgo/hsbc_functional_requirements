package io.prophecy.pipelines.parquetscd.graph.GenerateRandomIncrements_1

import io.prophecy.libs._
import io.prophecy.pipelines.parquetscd.config.ConfigStore._
import io.prophecy.pipelines.parquetscd.udfs.UDFs._
import io.prophecy.pipelines.parquetscd.udfs._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object DropRandomId {

  def apply(spark: SparkSession, in: DataFrame): DataFrame =
    in.drop("random_id")

}
