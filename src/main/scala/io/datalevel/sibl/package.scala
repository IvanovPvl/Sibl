package io.datalevel

package object sibl {
  implicit class ByteArrayImpr(val a: Array[Byte]) {
    def asHexString: String = {
      def byteToHex(b: Byte): String = Integer.toHexString(0xFF & b)
      a.foldLeft(StringBuilder.newBuilder) { (sb, el) =>
        if ((0xFF & el) < 0x10)
          sb.append("0" + byteToHex(el))
        else
          sb.append(byteToHex(el))
      }.toString
    }
  }
}
