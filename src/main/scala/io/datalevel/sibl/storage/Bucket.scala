package io.datalevel.sibl.storage

import io.datalevel.sibl.Block
import org.rocksdb.{Options, RocksDB}

import scala.collection.mutable
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

sealed trait Bucket {
  protected val lastKey: Array[Byte] = "last".getBytes

  def lastBlock(implicit serializer: Serializer[Block]): Future[Option[Block]]
  def getBlock(hash: Array[Byte])(implicit serializer: Serializer[Block]): Future[Option[Block]]
  def storeBlock(block: Block)(implicit serializer: Serializer[Block]): Future[Unit]
}

class RocksDbBucket(options: Options, databasePath: String) extends Bucket {
  private val store: RocksDB = RocksDB.open(options, databasePath)

  RocksDB.loadLibrary()

  def lastBlock(implicit serializer: Serializer[Block]): Future[Option[Block]] = {
    Future {
      val data = store.get(lastKey)
      if (data != null) Some(serializer.fromBinary(data)) else None
    }
  }

  def getBlock(hash: Array[Byte])(implicit serializer: Serializer[Block]): Future[Option[Block]] = {
    Future {
      val data = store.get(hash)
      if (data != null) Some(serializer.fromBinary(data)) else None
    }
  }

  def storeBlock(block: Block)(implicit serializer: Serializer[Block]): Future[Unit] = {
    Future {
      store.put(block.hash, serializer.toBinary(block))
    }
  }
}

class InMemoryBucket extends Bucket {
  private val store: mutable.Map[Array[Byte], Block] = mutable.Map.empty

  def lastBlock(implicit serializer: Serializer[Block]): Future[Option[Block]] = {
    Future {
      store.get(lastKey)
    }
  }

  def getBlock(hash: Array[Byte])(implicit serializer: Serializer[Block]): Future[Option[Block]] = {
    Future {
      store.get(hash)
    }
  }

  def storeBlock(block: Block)(implicit serializer: Serializer[Block]): Future[Unit] = {
    Future {
      store += (block.hash -> block)
    }
  }
}
