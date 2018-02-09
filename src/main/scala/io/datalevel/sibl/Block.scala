package io.datalevel.sibl

import java.time.Instant
import java.security.MessageDigest

case class Block(timestamp: Instant, data: String, prevBlockHash: Array[Byte], hash: Array[Byte]) {
  override def toString = s"timestamp: ${timestamp.toString}\ndata: $data\nprevBlockHash: ${prevBlockHash.asHexString}\nhash: ${hash.asHexString}"
}

object Block {
  def apply(data: String, prevHashBlock: Array[Byte]): Block = {
    val timestamp = Instant.now()
    val hash = MessageDigest.getInstance("SHA-256").digest(timestamp.toString.getBytes ++ data.getBytes ++ prevHashBlock)
    Block(timestamp, data, prevHashBlock, hash)
  }
}

