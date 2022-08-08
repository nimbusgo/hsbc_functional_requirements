package io.prophecy.pipelines.operatorsandgems

import io.prophecy.libs._
import io.prophecy.pipelines.operatorsandgems.config.ConfigStore._
import io.prophecy.pipelines.operatorsandgems.config._
import io.prophecy.pipelines.operatorsandgems.udfs.UDFs._
import io.prophecy.pipelines.operatorsandgems.udfs._
import io.prophecy.pipelines.operatorsandgems.graph._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object Main {

  def apply(spark: SparkSession): Unit = {
    val df_tpch_nation = tpch_nation(spark)
    NationLookup(spark, df_tpch_nation)
    val df_tpch_orders   = tpch_orders(spark)
    val df_AggByCustomer = AggByCustomer(spark, df_tpch_orders)
    val df_tpch_customer = tpch_customer(spark)
    val df_WithNtnName   = WithNtnName(spark,   df_tpch_customer)
    val (df_FilterUK_US_1_out0, df_FilterUK_US_1_out1) =
      FilterUK_US_1(spark, df_WithNtnName)
    val df_Union     = Union(spark,     df_FilterUK_US_1_out0, df_FilterUK_US_1_out1)
    val df_RightJoin = RightJoin(spark, df_AggByCustomer,      df_Union)
    val (df_FilterUK_US_2_out0, df_FilterUK_US_2_out1) =
      FilterUK_US_2(spark, df_WithNtnName)
    val df_tpch_part        = tpch_part(spark)
    val df_AggregateByBrand = AggregateByBrand(spark, df_tpch_part)
    val df_InnerJoin =
      InnerJoin(spark,              df_FilterUK_US_2_out0, df_FilterUK_US_2_out1)
    us_uk_inner_join_example(spark, df_InnerJoin)
    val df_OrderByBrandPrice = OrderByBrandPrice(spark, df_tpch_part)
    val df_LeftJoin          = LeftJoin(spark,          df_Union,     df_AggByCustomer)
    val df_Intersection      = Intersection(spark,      df_RightJoin, df_LeftJoin)
    order_by_example(spark, df_OrderByBrandPrice)
    val df_FullOuterJoin =
      FullOuterJoin(spark,          df_FilterUK_US_2_out0, df_FilterUK_US_2_out1)
    us_uk_outer_join_example(spark, df_FullOuterJoin)
    group_by_brand(spark,           df_AggregateByBrand)
    us_uk_union_example(spark,      df_Intersection)
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
      .set("prophecy.metadata.pipeline.uri", "2678/pipelines/OperatorsAndGems")
    MetricsCollector.start(spark,            "2678/pipelines/OperatorsAndGems")
    apply(spark)
    MetricsCollector.end(spark)
  }

}
