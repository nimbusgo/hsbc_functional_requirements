package io.prophecy.pipelines.scdmerge.graph

import io.prophecy.libs._
import io.prophecy.pipelines.scdmerge.config.ConfigStore._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import java.time._

object customers_scd2_write {

  def apply(spark: SparkSession, in: DataFrame): Unit = {
    import _root_.io.delta.tables._
    if (
      DeltaTable.isDeltaTable(spark,
                              "/data/tmp/hsbc/tpch-examples/scd2_customers"
      )
    ) {
      val updatesDF = in
        .withColumn("is_old_value", lit("true"))
        .withColumn("is_current",   lit("true"))
      val existingTable: DeltaTable =
        DeltaTable.forPath(spark, "/data/tmp/hsbc/tpch-examples/scd2_customers")
      val existingDF: DataFrame = existingTable.toDF
      val stagedUpdatesDF = updatesDF
        .join(existingDF, List("customer_id"))
        .where(
          existingDF.col("is_current") === lit("true") && List(
            existingDF.col("tax_id") =!= updatesDF.col("tax_id"),
            existingDF.col("tax_code") =!= updatesDF.col("tax_code"),
            existingDF.col("customer_name") =!= updatesDF.col("customer_name"),
            existingDF.col("state") =!= updatesDF.col("state")
          ).reduce((c1, c2) => c1 || c2)
        )
        .select(updatesDF.columns.map(x => updatesDF.col(x)): _*)
        .withColumn("is_old_value",             lit("false"))
        .withColumn("mergeKey",                 lit(null))
        .union(updatesDF.withColumn("mergeKey", concat(col("customer_id"))))
      existingTable
        .as("existingTable")
        .merge(
          stagedUpdatesDF.as("staged_updates"),
          concat(existingDF.col("customer_id")) === stagedUpdatesDF("mergeKey")
        )
        .whenMatched(
          existingDF.col("is_current") === lit("true") && List(
            existingDF.col("tax_id") =!= stagedUpdatesDF.col("tax_id"),
            existingDF.col("tax_code") =!= stagedUpdatesDF.col("tax_code"),
            existingDF.col("customer_name") =!= stagedUpdatesDF
              .col("customer_name"),
            existingDF.col("state") =!= stagedUpdatesDF.col("state")
          ).reduce((c1, c2) => c1 || c2)
        )
        .updateExpr(
          Map("is_current" → "false", "end_time" → "staged_updates.from_time")
        )
        .whenNotMatched()
        .insertAll()
        .execute()
    } else
      in.write
        .format("delta")
        .mode("overwrite")
        .save("/data/tmp/hsbc/tpch-examples/scd2_customers")
  }

}
