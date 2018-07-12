package io.datalevel

import java.nio.ByteBuffer
import java.nio.charset.Charset

import scala.util.{Failure, Success, Try}

package object sibl {
  val targetBits = 22

  implicit class ByteArrayOps(val a: Array[Byte]) {
    def asHexString: String = {
      def byteToHex(b: Byte): String = Integer.toHexString(0xFF & b)
      a.foldLeft(StringBuilder.newBuilder) { (sb, el) =>
        if ((0xFF & el) < 0x10)
          sb.append("0" + byteToHex(el))
        else
          sb.append(byteToHex(el))
      }.toString
    }

    def utf8String: String = new String(a, Charset.forName("UTF-8"))
  }

  implicit class LongOps(val n: Long) {
    def toByteArray: Array[Byte] = {
      val bb = ByteBuffer.allocate(java.lang.Long.BYTES)
      bb.putLong(n)
      bb.array
    }

    def toByteArrayOfHexString: Array[Byte] = n.toHexString.getBytes("UTF-8")
  }

  def loan[A <: AutoCloseable, B](resource: A)(block: A => B): B = {
    Try(block(resource)) match {
      case Success(result) =>
        resource.close()
        result
      case Failure(e) =>
        resource.close()
        throw e
    }
  }
}
