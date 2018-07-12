package io.datalevel.sibl

import tsec.messagedigests._
import tsec.messagedigests.imports.SHA256

import scala.annotation.tailrec

final case class RunResult(nonce: Long, hash: Array[Byte])

class ProofOfWork(val block: Block, val target: BigInt) {
  def prepare(nonce: Long): Array[Byte] = {
    val timeBytes       = block.timestamp.getEpochSecond.toByteArrayOfHexString
    val nonceBytes      = nonce.toByteArrayOfHexString
    val targetBitsBytes = targetBits.toByteArrayOfHexString
    block.prevBlockHash ++ block.data ++ timeBytes ++ targetBitsBytes ++ nonceBytes
  }

  def validate: Boolean = {
    val data    = prepare(block.nonce)
    val hash    = data.hash[SHA256]
    val hashInt = BigInt(1, hash)
    hashInt < target
  }

  def run: RunResult = runUtil(0)

  @tailrec
  private def runUtil(nonce: Long): RunResult = {
    val data       = prepare(nonce)
    val newHash    = data.hash[SHA256]
    val newHashInt = BigInt(1, newHash)

    if (newHashInt < target || nonce == Long.MaxValue)
      RunResult(nonce, newHash)
    else
      runUtil(nonce + 1)
  }
}

object ProofOfWork {
  def apply(block: Block): ProofOfWork = {
    val target = BigInt(1)
    new ProofOfWork(block, target << (256 - targetBits))
  }
}
