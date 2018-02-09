package io.datalevel.sibl

case class Blockchain(blocks: List[Block]) {
  def :+(data: String): Blockchain = {
    val newBlock = Block(data, blocks.last.hash)
    Blockchain(blocks :+ newBlock)
  }
}

object Blockchain {
  def apply(): Blockchain = {
    val genBlock = Block("Genesis Block", Array.emptyByteArray)
    Blockchain(List(genBlock))
  }
}
