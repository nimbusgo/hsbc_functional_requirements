package io.prophecy.pipelines.operatorsandgems.config

import io.prophecy.pipelines.operatorsandgems.config.ConfigStore._
import pureconfig._
import io.prophecy.libs._
case class Config(fabricName: String) extends ConfigBase
