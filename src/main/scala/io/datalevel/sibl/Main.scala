package io.datalevel.sibl

object Main extends App {
  val blockChain = Blockchain() :+ "Send 1 BTC to Pavel"
  blockChain.blocks foreach { println(_) }
}

