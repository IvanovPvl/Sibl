package io.datalevel.sibl

import java.time.Instant
import tsec.messagedigests._
import tsec.messagedigests.imports.SHA256

final case class Block(
  timestamp: Instant,
  data: Array[Byte],
  prevBlockHash: Array[Byte],
  hash: Array[Byte],
  nonce: Long) {
  override def toString: String = {
    val timeStr     = timestamp.toString
    val hashStr     = hash.asHexString
    val prevHashStr = prevBlockHash.asHexString

    s"timestamp: $timeStr\ndata: ${data.utf8String}\nprevBlockHash: $prevHashStr\nhash: $hashStr\nnonce: $nonce"
  }
}

object Block {
  def apply(data: Array[Byte], prevHashBlock: Array[Byte]): Block = {
    val timestamp = Instant.now()
    val headers   = (data ++ prevHashBlock ++ timestamp.getEpochSecond.toByteArrayOfHexString).hash[SHA256]
    val block     = Block(timestamp, data, prevHashBlock, headers, 0L)
    val pow       = ProofOfWork(block)
    val runResult = pow.run
    block.copy(hash = runResult.hash, nonce = runResult.nonce)
  }
}
