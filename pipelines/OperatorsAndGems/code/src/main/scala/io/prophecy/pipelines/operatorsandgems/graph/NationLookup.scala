package io.prophecy.pipelines.operatorsandgems.graph

import io.prophecy.libs._
import io.prophecy.pipelines.operatorsandgems.config.ConfigStore._
import io.prophecy.pipelines.operatorsandgems.udfs.UDFs._
import io.prophecy.pipelines.operatorsandgems.udfs._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import java.time._

object NationLookup {

  def apply(spark: SparkSession, in0: DataFrame): Unit =
    createLookup("Nation",
                 in0,
                 spark,
                 List("n_nationkey"),
                 "n_comment",
                 "n_name",
                 "n_regionkey"
    )

}
