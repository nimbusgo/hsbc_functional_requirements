package io.prophecy.pipelines.generaltests.graph

import io.prophecy.libs._
import io.prophecy.pipelines.generaltests.config.ConfigStore._
import io.prophecy.pipelines.generaltests.udfs.UDFs._
import io.prophecy.pipelines.generaltests.udfs._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import java.time._

object ValidateRules {

  def apply(spark: SparkSession, in: DataFrame): (DataFrame, DataFrame) = {
    import org.apache.spark.sql.functions._
    (in.withColumn(
         "extra_whitespace",
         when(trim(col("extra_whitespace")) === col("extra_whitespace") === lit(
                true
              ),
              col("extra_whitespace")
         ).otherwise(trim(col("extra_whitespace")))
       )
       .withColumn(
         "all_upper",
         when(upper(col("all_upper")) === col("all_upper") === lit(true),
              col("all_upper")
         ).otherwise(lit(null))
       )
       .withColumn(
         "all_lower",
         when(lower(col("all_lower")) === col("all_lower") === lit(true),
              col("all_lower")
         ).otherwise(lit(null))
       )
       .withColumn(
         "c_name",
         when(expr("replace(c_name, 'Customer#', 'CustomerNum')") === col(
                "c_name"
              ) === lit(true),
              col("c_name")
         ).otherwise(expr("replace(c_name, 'Customer#', 'CustomerNum')"))
       )
       .withColumn("extra_chars",
                   when(expr("trim('-', extra_chars)") === col(
                          "extra_chars"
                        ) === lit(true),
                        col("extra_chars")
                   ).otherwise(lit(null))
       )
       .withColumn(
         "all_upper",
         when(substring(col("all_upper"), 0, 4) === lit("CUST") === lit(true),
              col("all_upper")
         ).otherwise(lit(null))
       )
       .withColumn("sometimes_null",
                   when(col("sometimes_null").isNotNull === lit(true),
                        col("sometimes_null")
                   ).otherwise(lit(null))
       )
       .withColumn("sometimes_empty",
                   when(col("sometimes_empty") =!= lit("") === lit(true),
                        col("sometimes_empty")
                   ).otherwise(lit(null))
       )
       .withColumn("date_example",
                   when(to_date(col("date_example"),
                                "yyyy-MM-dd"
                        ).isNotNull === lit(true),
                        col("date_example")
                   ).otherwise(lit(null))
       ), {
       val iter_0 = in
         .filter(
           List(
             trim(col("extra_whitespace")) === col("extra_whitespace") === lit(
               false
             ),
             upper(col("all_upper")) === col("all_upper") === lit(false),
             lower(col("all_lower")) === col("all_lower") === lit(false),
             expr("replace(c_name, 'Customer#', 'CustomerNum')") === col(
               "c_name"
             ) === lit(false),
             expr("trim('-', extra_chars)") === col("extra_chars") === lit(
               false
             ),
             substring(col("all_upper"), 0, 4) === lit("CUST") === lit(false),
             col("sometimes_null").isNotNull === lit(false),
             col("sometimes_empty") =!= lit("") === lit(false),
             to_date(col("date_example"), "yyyy-MM-dd").isNotNull === lit(false)
           ).reduce(_ || _)
         )
         .withColumn("rule_broken", lit("None"))
       val iter_1 = {
         val result2 = iter_0
         locally {
           val isRuleBroken = when(trim(col("extra_whitespace")) === col(
                                     "extra_whitespace"
                                   ) === lit(false),
                                   lit("is_trimmed")
           ).otherwise(lit("None"))
           result2.withColumn(
             "rule_broken",
             when(col("rule_broken") === lit("None"),  isRuleBroken).otherwise(
               when(isRuleBroken === lit("None"),      col("rule_broken"))
                 .otherwise(concat(col("rule_broken"), lit(", "), isRuleBroken))
             )
           )
         }
       }
       val iter_2 = {
         val result2 = iter_1
         locally {
           val isRuleBroken =
             when(upper(col("all_upper")) === col("all_upper") === lit(false),
                  lit("is_upper")
             ).otherwise(lit("None"))
           result2.withColumn(
             "rule_broken",
             when(col("rule_broken") === lit("None"),  isRuleBroken).otherwise(
               when(isRuleBroken === lit("None"),      col("rule_broken"))
                 .otherwise(concat(col("rule_broken"), lit(", "), isRuleBroken))
             )
           )
         }
       }
       val iter_3 = {
         val result2 = iter_2
         locally {
           val isRuleBroken =
             when(lower(col("all_lower")) === col("all_lower") === lit(false),
                  lit("is_lower")
             ).otherwise(lit("None"))
           result2.withColumn(
             "rule_broken",
             when(col("rule_broken") === lit("None"),  isRuleBroken).otherwise(
               when(isRuleBroken === lit("None"),      col("rule_broken"))
                 .otherwise(concat(col("rule_broken"), lit(", "), isRuleBroken))
             )
           )
         }
       }
       val iter_4 = {
         val result2 = iter_3
         locally {
           val isRuleBroken =
             when(expr("replace(c_name, 'Customer#', 'CustomerNum')") === col(
                    "c_name"
                  ) === lit(false),
                  lit("replace_num")
             ).otherwise(lit("None"))
           result2.withColumn(
             "rule_broken",
             when(col("rule_broken") === lit("None"),  isRuleBroken).otherwise(
               when(isRuleBroken === lit("None"),      col("rule_broken"))
                 .otherwise(concat(col("rule_broken"), lit(", "), isRuleBroken))
             )
           )
         }
       }
       val iter_5 = {
         val result2 = iter_4
         locally {
           val isRuleBroken = when(expr("trim('-', extra_chars)") === col(
                                     "extra_chars"
                                   ) === lit(false),
                                   lit("trim_spl_character")
           ).otherwise(lit("None"))
           result2.withColumn(
             "rule_broken",
             when(col("rule_broken") === lit("None"),  isRuleBroken).otherwise(
               when(isRuleBroken === lit("None"),      col("rule_broken"))
                 .otherwise(concat(col("rule_broken"), lit(", "), isRuleBroken))
             )
           )
         }
       }
       val iter_6 = {
         val result2 = iter_5
         locally {
           val isRuleBroken = when(
             substring(col("all_upper"), 0, 4) === lit("CUST") === lit(false),
             lit("substring_rule")
           ).otherwise(lit("None"))
           result2.withColumn(
             "rule_broken",
             when(col("rule_broken") === lit("None"),  isRuleBroken).otherwise(
               when(isRuleBroken === lit("None"),      col("rule_broken"))
                 .otherwise(concat(col("rule_broken"), lit(", "), isRuleBroken))
             )
           )
         }
       }
       val iter_7 = {
         val result2 = iter_6
         locally {
           val isRuleBroken =
             when(col("sometimes_null").isNotNull === lit(false),
                  lit("check_null")
             ).otherwise(lit("None"))
           result2.withColumn(
             "rule_broken",
             when(col("rule_broken") === lit("None"),  isRuleBroken).otherwise(
               when(isRuleBroken === lit("None"),      col("rule_broken"))
                 .otherwise(concat(col("rule_broken"), lit(", "), isRuleBroken))
             )
           )
         }
       }
       val iter_8 = {
         val result2 = iter_7
         locally {
           val isRuleBroken =
             when(col("sometimes_empty") =!= lit("") === lit(false),
                  lit("check_empty")
             ).otherwise(lit("None"))
           result2.withColumn(
             "rule_broken",
             when(col("rule_broken") === lit("None"),  isRuleBroken).otherwise(
               when(isRuleBroken === lit("None"),      col("rule_broken"))
                 .otherwise(concat(col("rule_broken"), lit(", "), isRuleBroken))
             )
           )
         }
       }
       locally {
         val result2 = iter_8
         locally {
           val isRuleBroken = when(to_date(col("date_example"),
                                           "yyyy-MM-dd"
                                   ).isNotNull === lit(false),
                                   lit("check_date_format")
           ).otherwise(lit("None"))
           result2.withColumn(
             "rule_broken",
             when(col("rule_broken") === lit("None"),  isRuleBroken).otherwise(
               when(isRuleBroken === lit("None"),      col("rule_broken"))
                 .otherwise(concat(col("rule_broken"), lit(", "), isRuleBroken))
             )
           )
         }
       }
     }
    )
  }

}
