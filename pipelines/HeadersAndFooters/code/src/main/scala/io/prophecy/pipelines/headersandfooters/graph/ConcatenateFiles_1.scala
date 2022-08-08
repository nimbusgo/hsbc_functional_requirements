package io.prophecy.pipelines.headersandfooters.graph

import io.prophecy.libs._
import io.prophecy.pipelines.headersandfooters.config.ConfigStore._
import io.prophecy.pipelines.headersandfooters.udfs.UDFs._
import io.prophecy.pipelines.headersandfooters.udfs._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object ConcatenateFiles_1 {

  def apply(spark: SparkSession): Unit = {
    import _root_.io.prophecy.libs.concatenate
    concatenate(
      List(
        "/data/tmp/hsbc/tpch-examples/header_footers/header",
        "dbfs:/databricks-datasets/retail-org/customers/customers.csv",
        "/data/tmp/hsbc/tpch-examples/header_footers/footer"
      ),
      "/data/tmp/hsbc/tpch-examples/header_footers/concatenated2"
    )
  }

}
