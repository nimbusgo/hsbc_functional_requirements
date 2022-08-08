package io.prophecy.pipelines.scdmerge

import io.prophecy.libs._
import io.prophecy.pipelines.scdmerge.config.ConfigStore._
import io.prophecy.pipelines.scdmerge.config._
import io.prophecy.pipelines.scdmerge.udfs.UDFs._
import io.prophecy.pipelines.scdmerge.udfs._
import io.prophecy.pipelines.scdmerge.graph._
import io.prophecy.pipelines.scdmerge.graph.GenerateRandomIncrements_0
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object Main {

  def apply(spark: SparkSession): Unit = {
    val df_GenerateRandomIncrements_0 = GenerateRandomIncrements_0.apply(spark)
    val df_AddScd2Fields              = AddScd2Fields(spark, df_GenerateRandomIncrements_0)
    customers_scd2_write(spark, df_AddScd2Fields)
    val df_customers_scd2 = customers_scd2(spark)
    val df_Identity       = Identity(spark, df_customers_scd2)
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
      .set("prophecy.metadata.pipeline.uri", "2678/pipelines/scd2_merge")
    MetricsCollector.start(spark,            "2678/pipelines/scd2_merge")
    apply(spark)
    MetricsCollector.end(spark)
  }

}
