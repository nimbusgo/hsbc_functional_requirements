package io.prophecy.pipelines.generaltests.graph

import io.prophecy.libs._
import io.prophecy.pipelines.generaltests.config.ConfigStore._
import io.prophecy.pipelines.generaltests.udfs.UDFs._
import io.prophecy.pipelines.generaltests.udfs._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object DeduplicateLast {

  def apply(spark: SparkSession, in: DataFrame): DataFrame = {
    import org.apache.spark.sql.expressions.Window
    in.withColumn("row_number",
                  row_number().over(
                    Window
                      .partitionBy("c_custkey")
                      .orderBy(lit(1))
                      .rowsBetween(Window.unboundedPreceding, Window.currentRow)
                  )
      )
      .withColumn(
        "count",
        count("*").over(
          Window
            .partitionBy("c_custkey")
            .orderBy(lit(1))
            .rowsBetween(Window.unboundedPreceding, Window.unboundedFollowing)
        )
      )
      .filter(col("row_number") === col("count"))
      .drop("row_number")
      .drop("count")
  }

}
