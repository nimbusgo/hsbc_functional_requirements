package io.prophecy.pipelines.sqloperators.graph

import io.prophecy.libs._
import io.prophecy.pipelines.sqloperators.config.ConfigStore._
import io.prophecy.pipelines.sqloperators.udfs.UDFs._
import io.prophecy.pipelines.sqloperators.udfs._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object SqlExpressionChecks {

  def apply(spark: SparkSession, in: DataFrame): DataFrame =
    in.select(
      lit("123").cast(IntegerType).as("try_cast_int_success"),
      lit("abc").cast(IntegerType).as("try_cast_int_failure"),
      lit("1.5").cast(FloatType).as("try_cast_float_success"),
      lit("abc").cast(FloatType).as("try_cast_float_failure"),
      lit("2020-01-01").cast(DateType).as("try_cast_date_success"),
      lit("1234").cast(DateType).as("try_cast_date_failure"),
      format_number(lit(1.246d),  2).as("format_check"),
      date_format(current_date(), "yyyy/MM/dd").as("date_convert_format"),
      to_timestamp(lit(java.time.LocalDate.parse("1970-01-01")))
        .as("date_convert_timestamp"),
      unix_timestamp(lit(java.time.LocalDate.parse("1970-01-02")))
        .as("date_convert_unix_timestamp"),
      to_date(lit("20015"), "yyDDD").as("julian_date_convert"),
      coalesce(lit(null),   col("c_phone"), lit("123")).as("coalesce_check"),
      lit("abc").isNull.as("isnull_check"),
      lit("abc").isNotNull.as("isnotnull_check"),
      when(col("c_acctbal") > lit(1000), lit("sufficient balance"))
        .otherwise(lit("insufficient balance"))
        .as("case_check"),
      (!col("c_nationkey").isin(19, 20)).as("not_in_check"),
      col("c_nationkey").isin(19, 20).as("in_check"),
      (col("c_nationkey") =!= lit(19)).as("not_equals_check"),
      (col("c_nationkey") === lit(19)).as("equals_check"),
      (col("c_acctbal") * lit(2.5d)).as("mult_check"),
      (col("c_acctbal") + lit(1000)).as("plus_check"),
      (col("c_acctbal") / lit(10)).as("div_check"),
      (col("c_acctbal") > lit(1000)).as("gt_check"),
      (col("c_acctbal") < lit(1000)).as("lt_check"),
      abs(col("c_acctbal") - lit(1000)).as("abs_check"),
      (col("c_acctbal") > lit(0))
        .and(col("c_acctbal") < lit(1000))
        .as("and_check"),
      (col("c_acctbal") < lit(0))
        .or(col("c_acctbal") > lit(1000))
        .as("or_check"),
      col("c_name"),
      col("c_name").like("%41244%").as("like_check"),
      substring(col("c_name"), 8, 4).as("substring_check"),
      length(col("c_name")).as("len_check"),
      concat(col("c_name"), lit("|"), col("c_address")).as("concat_check"),
      expr("replace('ABCabc', 'abc', 'DEF')").as("replace_check"),
      expr("left(c_name, 8)").as("left_check"),
      expr("right(c_name, 8)").as("right_check"),
      instr(lit("abcdefgh"),      "e").as("charindex_check"),
      date_add(current_date(),    5).as("date_add_check"),
      datediff(lit("2009-07-30"), lit("2009-07-31")).as("date_diff_check")
    )

}
