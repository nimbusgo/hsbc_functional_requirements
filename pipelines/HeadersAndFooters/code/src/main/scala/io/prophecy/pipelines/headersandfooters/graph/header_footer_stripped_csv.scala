package io.prophecy.pipelines.headersandfooters.graph

import io.prophecy.libs._
import io.prophecy.pipelines.headersandfooters.config.ConfigStore._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object header_footer_stripped_csv {

  def apply(spark: SparkSession): DataFrame =
    spark.read
      .format("csv")
      .option("header", true)
      .option("sep",    ",")
      .schema(
        StructType(
          Array(
            StructField("customer_id",     StringType, true),
            StructField("tax_id",          StringType, true),
            StructField("tax_code",        StringType, true),
            StructField("customer_name",   StringType, true),
            StructField("state",           StringType, true),
            StructField("city",            StringType, true),
            StructField("postcode",        StringType, true),
            StructField("street",          StringType, true),
            StructField("number",          StringType, true),
            StructField("unit",            StringType, true),
            StructField("region",          StringType, true),
            StructField("district",        StringType, true),
            StructField("lon",             StringType, true),
            StructField("lat",             StringType, true),
            StructField("ship_to_address", StringType, true),
            StructField("valid_from",      StringType, true),
            StructField("valid_to",        StringType, true),
            StructField("units_purchased", StringType, true),
            StructField("loyalty_segment", StringType, true)
          )
        )
      )
      .load("/data/tmp/hsbc/tpch-examples/header_footers/stripped")

}
