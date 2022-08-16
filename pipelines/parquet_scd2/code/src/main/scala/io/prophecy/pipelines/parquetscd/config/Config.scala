package io.prophecy.pipelines.parquetscd.config

import io.prophecy.pipelines.parquetscd.config.ConfigStore._
import pureconfig._
import io.prophecy.libs._
case class Config(fabricName: String) extends ConfigBase
