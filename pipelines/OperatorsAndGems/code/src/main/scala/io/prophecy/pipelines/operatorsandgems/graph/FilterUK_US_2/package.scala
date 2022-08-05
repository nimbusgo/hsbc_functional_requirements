package io.prophecy.pipelines.operatorsandgems.graph

import io.prophecy.libs._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
package object FilterUK_US_2 {

  def apply(spark: SparkSession, in0: DataFrame): Subgraph2 = {
    val df_FilterUS_1 = FilterUS_1(spark, in0)
    val df_FilterUK_1 = FilterUK_1(spark, in0)
    (df_FilterUK_1, df_FilterUS_1)
  }

}
