package io.prophecy.pipelines.sqloperators.graph

import io.prophecy.libs._
import io.prophecy.pipelines.sqloperators.config.ConfigStore._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object tpch_sql_examples {

  def apply(spark: SparkSession, in: DataFrame): Unit = {
    import _root_.io.delta.tables._
    in.write
      .format("delta")
      .mode("overwrite")
      .save("/data/tmp/hsbc/tpch-examples/sql-examples")
  }

}
