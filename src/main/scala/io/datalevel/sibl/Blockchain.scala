package io.datalevel.sibl

import tsec.common._

case class Blockchain(blocks: List[Block]) {
  def :+(data: String): Blockchain = {
    val newBlock = Block(data.utf8Bytes, blocks.last.hash)
    Blockchain(blocks :+ newBlock)
  }
}

object Blockchain {
  def apply(): Blockchain = {
    val genBlock = Block("Genesis Block".utf8Bytes, Array.emptyByteArray)
    Blockchain(List(genBlock))
  }
}
