package io.datalevel.sibl

object Main extends App {
  val blockChain = Blockchain() :+ "Send 1 BTC to Pavel" :+ "Send 2 BTC to Ivan"
  blockChain.blocks foreach { println(_) }
}
