package io.datalevel.sibl.utils

import pureconfig.loadConfig

case class Config(rocksDb: RocksDbConfig)

object Config {
  def load(): Config = loadConfig[Config] match {
    case Right(config) => config
    case Left(error) => throw new RuntimeException("Cannot read config file, errors:\n" + error.toList.mkString("\n"))
  }
}

private[utils] case class RocksDbConfig(databasePath: String)
