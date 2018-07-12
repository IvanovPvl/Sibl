package io.datalevel.sibl.storage

import io.datalevel.sibl.Block

import scala.concurrent.Future

sealed trait Bucket[A] {
  def lastBlock(implicit serializer: Serializer[A]): Future[Option[Block]]
  def getBlock(hash: Array[Byte])(implicit serializer: Serializer[A]): Future[Option[Block]]
  def storeBlock(block: Block)(implicit serializer: Serializer[A]): Future[Unit]
}

class RocksDbBucket[A] extends Bucket[A] {
  def lastBlock(implicit serializer: Serializer[A]): Future[Option[Block]] = ???
  def getBlock(hash: Array[Byte])(implicit serializer: Serializer[A]): Future[Option[Block]] = ???
  def storeBlock(block: Block)(implicit serializer: Serializer[A]): Future[Unit] = ???
}
