package io.prophecy.pipelines.parquetscd

import io.prophecy.libs._
import io.prophecy.pipelines.parquetscd.config.ConfigStore._
import io.prophecy.pipelines.parquetscd.config._
import io.prophecy.pipelines.parquetscd.udfs.UDFs._
import io.prophecy.pipelines.parquetscd.udfs._
import io.prophecy.pipelines.parquetscd.graph._
import io.prophecy.pipelines.parquetscd.graph.GenerateRandomIncrements_0
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import java.time._

object Main {

  def apply(spark: SparkSession): Unit = {
    val df_GenerateRandomIncrements_0 = GenerateRandomIncrements_0.apply(spark)
    val df_AddScd2Fields              = AddScd2Fields(spark, df_GenerateRandomIncrements_0)
    val df_scd2_customers_parquet_1   = scd2_customers_parquet_1(spark)
    val (df_SplitCurrent_out0, df_SplitCurrent_out1) =
      SplitCurrent(spark, df_scd2_customers_parquet_1)
    val df_HandleUpdates =
      HandleUpdates(spark, df_AddScd2Fields, df_SplitCurrent_out0)
    val (df_RowDistributor_1_updates,
         df_RowDistributor_1_inserts,
         df_RowDistributor_1_existing
    )                      = RowDistributor_1(spark, df_HandleUpdates)
    val df_unupdated       = unupdated(spark,        df_RowDistributor_1_updates)
    val df_updated         = updated(spark,          df_RowDistributor_1_updates)
    val df_updates         = updates(spark,          df_updated)
    val df_retainUnupdated = retainUnupdated(spark,  df_unupdated)
    val df_historical      = historical(spark,       df_updated)
    val df_inserts         = inserts(spark,          df_RowDistributor_1_inserts)
    val df_existing        = existing(spark,         df_RowDistributor_1_existing)
    val df_UnionSCD2 = UnionSCD2(spark,
                                 df_updates,
                                 df_historical,
                                 df_retainUnupdated,
                                 df_inserts,
                                 df_existing,
                                 df_SplitCurrent_out1
    )
    scd2_customers_parquet(spark, df_UnionSCD2)
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
      .set("prophecy.metadata.pipeline.uri", "2678/pipelines/parquet_scd2")
    MetricsCollector.start(spark,            "2678/pipelines/parquet_scd2")
    apply(spark)
    MetricsCollector.end(spark)
  }

}
