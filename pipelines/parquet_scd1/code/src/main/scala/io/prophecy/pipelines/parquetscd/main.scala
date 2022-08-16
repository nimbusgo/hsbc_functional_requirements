package io.prophecy.pipelines.parquetscd

import io.prophecy.libs._
import io.prophecy.pipelines.parquetscd.config.ConfigStore._
import io.prophecy.pipelines.parquetscd.config._
import io.prophecy.pipelines.parquetscd.udfs.UDFs._
import io.prophecy.pipelines.parquetscd.udfs._
import io.prophecy.pipelines.parquetscd.graph._
import io.prophecy.pipelines.parquetscd.graph.GenerateRandomIncrements_1
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object Main {

  def apply(spark: SparkSession): Unit = {
    val df_GenerateRandomIncrements_1 = GenerateRandomIncrements_1.apply(spark)
    val df_scd1_customers_parquet_1   = scd1_customers_parquet_1(spark)
    val df_Merge =
      Merge(spark,                df_GenerateRandomIncrements_1, df_scd1_customers_parquet_1)
    scd1_customers_parquet(spark, df_Merge)
  }

  def main(args: Array[String]): Unit = {
    ConfigStore.Config = ConfigurationFactoryImpl.fromCLI(args)
    val spark: SparkSession = SparkSession
      .builder()
      .appName("Prophecy Pipeline")
      .config("spark.default.parallelism",             "4")
      .config("spark.sql.legacy.allowUntypedScalaUDF", "true")
      .enableHiveSupport()
      .getOrCreate()
      .newSession()
    spark.conf
      .set("prophecy.metadata.pipeline.uri", "2678/pipelines/parquet_scd1")
    MetricsCollector.start(spark,            "2678/pipelines/parquet_scd1")
    apply(spark)
    MetricsCollector.end(spark)
  }

}
