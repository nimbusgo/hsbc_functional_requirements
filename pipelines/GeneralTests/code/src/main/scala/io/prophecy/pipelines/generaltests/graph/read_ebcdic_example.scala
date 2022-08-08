package io.prophecy.pipelines.generaltests.graph

import io.prophecy.libs._
import io.prophecy.pipelines.generaltests.config.ConfigStore._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object read_ebcdic_example {

  def apply(spark: SparkSession): DataFrame = {
    import _root_.io.prophecy.abinitio.dml.DMLSchema.parse
    import _root_.io.prophecy.libs.{FFSchemaRecord, _}
    import play.api.libs.json.Json
    import _root_.io.prophecy.libs.FixedFormatSchemaImplicits._
    spark.read
      .option(
        "schema",
        Some("""ebcdic record
string(18) c_name ;
decimal(10, 0) c_custkey ;
end""").map(s => parse(s).asInstanceOf[FFSchemaRecord])
          .map(s => Json.stringify(Json.toJson(s)))
          .getOrElse("")
      )
      .format("io.prophecy.libs.FixedFileFormat")
      .load("/data/tmp/hsbc/tpch-examples/ebcdic_example")
  }

}
