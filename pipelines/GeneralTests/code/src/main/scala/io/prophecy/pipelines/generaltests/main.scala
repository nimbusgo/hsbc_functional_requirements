package io.prophecy.pipelines.generaltests

import io.prophecy.libs._
import io.prophecy.pipelines.generaltests.config.ConfigStore._
import io.prophecy.pipelines.generaltests.config._
import io.prophecy.pipelines.generaltests.udfs.UDFs._
import io.prophecy.pipelines.generaltests.udfs._
import io.prophecy.pipelines.generaltests.graph._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object Main {

  def apply(spark: SparkSession): Unit = {
    val df_tpch_customer  = tpch_customer(spark)
    val df_CreateExamples = CreateExamples(spark, df_tpch_customer)
    val (df_ValidateRules_out0, df_ValidateRules_out1) =
      ValidateRules(spark,         df_CreateExamples)
    validation_rules_output(spark, df_ValidateRules_out1)
    val df_SelectEbdciddFields = SelectEbdciddFields(spark, df_tpch_customer)
    ebcdic_example(spark, df_SelectEbdciddFields)
    val df_CreateFirst = CreateFirst(spark, df_tpch_customer)
    val df_CreateLast  = CreateLast(spark,  df_tpch_customer)
    val df_CreateDuplicates =
      CreateDuplicates(spark, df_CreateFirst, df_CreateLast)
    validated_rows(spark,     df_ValidateRules_out0)
    val df_DeduplicateFirst = DeduplicateFirst(spark, df_CreateDuplicates)
    dedupe_first(spark, df_DeduplicateFirst)
    val df_DeduplicateLast = DeduplicateLast(spark, df_CreateDuplicates)
    dedupe_last(spark,     df_DeduplicateLast)
    parquet_example(spark, df_SelectEbdciddFields)
    val df_parquet_example_1   = parquet_example_1(spark)
    val df_read_ebcdic_example = read_ebcdic_example(spark)
    val df_Intersect =
      Intersect(spark, df_read_ebcdic_example, df_parquet_example_1)
    val df_ApplyUpperFlag = ApplyUpperFlag(spark, df_Intersect)
    config_based_ouput(spark, df_ApplyUpperFlag)
    intersect_example(spark,  df_Intersect)
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
      .set("prophecy.metadata.pipeline.uri", "2678/pipelines/GeneralTests")
    MetricsCollector.start(spark,            "2678/pipelines/GeneralTests")
    apply(spark)
    MetricsCollector.end(spark)
  }

}
