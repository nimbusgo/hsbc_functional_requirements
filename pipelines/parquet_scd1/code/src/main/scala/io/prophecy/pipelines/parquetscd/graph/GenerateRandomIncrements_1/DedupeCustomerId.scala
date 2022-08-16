package io.prophecy.pipelines.parquetscd.graph.GenerateRandomIncrements_1

import io.prophecy.libs._
import io.prophecy.pipelines.parquetscd.config.ConfigStore._
import io.prophecy.pipelines.parquetscd.udfs.UDFs._
import io.prophecy.pipelines.parquetscd.udfs._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object DedupeCustomerId {

  def apply(spark: SparkSession, in: DataFrame): DataFrame = {
    import org.apache.spark.sql.expressions.Window
    in.withColumn("row_number",
                  row_number().over(
                    Window
                      .partitionBy("customer_id")
                      .orderBy(lit(1))
                      .rowsBetween(Window.unboundedPreceding, Window.currentRow)
                  )
      )
      .filter(col("row_number") === lit(1))
      .drop("row_number")
  }

}
