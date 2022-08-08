package io.prophecy.pipelines.headersandfooters

import io.prophecy.libs._
import io.prophecy.pipelines.headersandfooters.config.ConfigStore._
import io.prophecy.pipelines.headersandfooters.config._
import io.prophecy.pipelines.headersandfooters.udfs.UDFs._
import io.prophecy.pipelines.headersandfooters.udfs._
import io.prophecy.pipelines.headersandfooters.graph._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object Main {

  def apply(spark: SparkSession): Unit = {
    val df_CreateHeader   = CreateHeader(spark)
    val df_customers_text = customers_text(spark)
    header_line(spark, df_CreateHeader)
    val df_CreateFooter = CreateFooter(spark)
    footer_line(spark, df_CreateFooter)
    val df_Concatenate =
      Concatenate(spark, df_CreateHeader, df_customers_text, df_CreateFooter)
    val df_Repartition = Repartition(spark, df_Concatenate)
    concatenated_output_1(spark, df_Repartition)
    ConcatenateFiles_1(spark)
    StripHeaderFooter_1(spark)
    val df_header_footer_stripped_csv = header_footer_stripped_csv(spark)
    val df_Identity                   = Identity(spark, df_header_footer_stripped_csv)
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
      .set("prophecy.metadata.pipeline.uri", "2678/pipelines/HeadersAndFooters")
    MetricsCollector.start(spark,            "2678/pipelines/HeadersAndFooters")
    apply(spark)
    MetricsCollector.end(spark)
  }

}
