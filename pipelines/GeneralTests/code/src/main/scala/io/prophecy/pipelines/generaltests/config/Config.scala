package io.prophecy.pipelines.generaltests.config

import io.prophecy.pipelines.generaltests.config.ConfigStore._
import pureconfig._
import io.prophecy.libs._

case class Config(fabricName: String, base_path: String, flag: String)
    extends ConfigBase
