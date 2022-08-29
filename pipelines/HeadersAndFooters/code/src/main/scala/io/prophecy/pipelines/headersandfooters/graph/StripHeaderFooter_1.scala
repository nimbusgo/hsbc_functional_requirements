package io.prophecy.pipelines.headersandfooters.graph

import io.prophecy.libs._
import io.prophecy.pipelines.headersandfooters.config.ConfigStore._
import io.prophecy.pipelines.headersandfooters.udfs.UDFs._
import io.prophecy.pipelines.headersandfooters.udfs._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import java.time._

object StripHeaderFooter_1 {

  def apply(spark: SparkSession): Unit = {
    import spark.implicits._
    val sourceText = spark.sparkContext.textFile(
      "/data/tmp/hsbc/tpch-examples/header_footers/concatenated"
    )
    val footerFilter = sourceText.count() - 1
    sourceText
      .zipWithIndex()
      .filter({
        case (_, idx) =>
          idx >= 1 && idx < footerFilter && idx < footerFilter + 1
      })
      .map({
        case (value, _) => value
      })
      .toDF("value")
      .write
      .mode("overwrite")
      .text("/data/tmp/hsbc/tpch-examples/header_footers/stripped")
  }

}
