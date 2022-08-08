package data.pipelines.scdmerge.graph.GenerateRandomIncrements_0

import io.prophecy.libs._
import io.prophecy.pipelines.scdmerge.config.ConfigStore._
import io.prophecy.pipelines.scdmerge.udfs.UDFs._
import io.prophecy.pipelines.scdmerge.udfs._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object AddRandomID {

  def apply(spark: SparkSession, in: DataFrame): DataFrame =
    in.withColumn("random_id", floor(lit(3) * rand()))

}
