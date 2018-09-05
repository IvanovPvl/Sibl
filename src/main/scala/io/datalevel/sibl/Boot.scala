package io.datalevel.sibl

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, ObjectInputStream, ObjectOutputStream}

import io.datalevel.sibl.storage.{DefaultSerializer, RocksDbBucket}
import org.rocksdb.{Options, RocksDB}

import scala.concurrent.ExecutionContext.Implicits.global
import io.datalevel.sibl.utils.Config

object Boot extends App {
  def startApplication() = {
    val config = Config.load()
    val options = new Options().setCreateIfMissing(true)
    implicit val serializer = new DefaultSerializer[Block]

//    val blockChain = Blockchain() :+ "Send 1 BTC to Pavel" :+ "Send 2 BTC to Ivan"
//    blockChain.blocks foreach { b =>
//      println(b)
//      val pow = ProofOfWork(b)
//      println(s"PoW: ${pow.validate}")
//    }

    val blockchain = Blockchain()
    val block = blockchain.blocks(0)
    val bucket = new RocksDbBucket(options, config.rocksDb.databasePath)
    for {
      _ <- bucket.storeBlock(block)
    }()
  }

  startApplication()
}
