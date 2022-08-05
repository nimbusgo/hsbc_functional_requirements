package io.prophecy.pipelines.sqloperators.config

import io.prophecy.pipelines.sqloperators.config.ConfigStore._
import pureconfig._
import io.prophecy.libs._
case class Config(fabricName: String) extends ConfigBase
