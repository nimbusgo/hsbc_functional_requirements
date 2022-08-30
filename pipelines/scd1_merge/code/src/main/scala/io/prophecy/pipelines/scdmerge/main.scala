package io.prophecy.pipelines.scdmerge

import io.prophecy.libs._
import io.prophecy.pipelines.scdmerge.config.ConfigStore._
import io.prophecy.pipelines.scdmerge.config._
import io.prophecy.pipelines.scdmerge.udfs.UDFs._
import io.prophecy.pipelines.scdmerge.udfs._
import io.prophecy.pipelines.scdmerge.graph._
import io.prophecy.pipelines.scdmerge.graph.GenerateRandomIncrements
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import java.time._

object Main {

  def apply(spark: SparkSession): Unit = {
    val df_GenerateRandomIncrements = GenerateRandomIncrements.apply(spark)
    val df_customers_scd1           = customers_scd1(spark)
    val df_OrderBy_1                = OrderBy_1(spark, df_customers_scd1)
    customers_scd1_write(spark, df_GenerateRandomIncrements)
    val df_Identity = Identity(spark, df_OrderBy_1)
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
      .set("prophecy.metadata.pipeline.uri", "2678/pipelines/scd1_merge")
    MetricsCollector.start(spark,            "2678/pipelines/scd1_merge")
    apply(spark)
    MetricsCollector.end(spark)
  }

}
