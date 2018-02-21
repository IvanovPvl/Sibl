package io.datalevel.sibl

import java.time.Instant
import java.security.MessageDigest

case class Block(timestamp: Instant, data: String, prevBlockHash: Array[Byte], hash: Array[Byte]) {
  override def toString = {
    val timeStr     = timestamp.toString
    val hashStr     = hash.asHexString
    val prevHashStr = prevBlockHash.asHexString

    s"timestamp: $timeStr\ndata: $data\nprevBlockHash: $prevHashStr\nhash: $hashStr"
  }
}

object Block {
  def apply(data: String, prevHashBlock: Array[Byte]): Block = {
    val timestamp = Instant.now()
    val hash = MessageDigest.getInstance("SHA-256").digest(timestamp.toString.getBytes ++ data.getBytes ++ prevHashBlock)
    Block(timestamp, data, prevHashBlock, hash)
  }
}
