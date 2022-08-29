package io.prophecy.pipelines.sqloperators

import io.prophecy.libs._
import io.prophecy.pipelines.sqloperators.config.ConfigStore._
import io.prophecy.pipelines.sqloperators.config._
import io.prophecy.pipelines.sqloperators.udfs.UDFs._
import io.prophecy.pipelines.sqloperators.udfs._
import io.prophecy.pipelines.sqloperators.graph._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import java.time._

object Main {

  def apply(spark: SparkSession): Unit = {
    val df_tpch_customer  = tpch_customer(spark)
    val df_WindowFunction = WindowFunction(spark, df_tpch_customer)
    val df_SelectDistinct = SelectDistinct(spark, df_tpch_customer)
    val df_SelectFields   = SelectFields(spark,   df_SelectDistinct)
    val df_AggChecks      = AggChecks(spark,      df_tpch_customer)
    aggregate_examples(spark, df_AggChecks)
    val df_SqlExpressionChecks = SqlExpressionChecks(spark, df_tpch_customer)
    tpch_sql_examples(spark,       df_SqlExpressionChecks)
    row_number_example(spark,      df_WindowFunction)
    select_distinct_example(spark, df_SelectFields)
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
      .set("prophecy.metadata.pipeline.uri", "2678/pipelines/SQLOperators")
    MetricsCollector.start(spark,            "2678/pipelines/SQLOperators")
    apply(spark)
    MetricsCollector.end(spark)
  }

}
