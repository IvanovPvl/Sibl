package io.datalevel.sibl.storage

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

import com.sksamuel.avro4s._

import io.datalevel.sibl.loan

sealed trait Serializer[A] {
  def toBinary(data: A): Array[Byte]
  def fromBinary(buffer: Array[Byte]): A
}

class AvroSerializer[A : SchemaFor : ToRecord : FromRecord] extends Serializer[A] {
  def toBinary(data: A): Array[Byte] = {
    loan(new ByteArrayOutputStream()) { baos =>
      loan(AvroOutputStream.binary[A](baos)) { output =>
        output.write(data)
        baos.toByteArray
      }
    }
  }

  def fromBinary(buffer: Array[Byte]): A = {
    loan(new ByteArrayInputStream(buffer)) { in =>
      loan(AvroInputStream.binary[A](in)) { input =>
        input.iterator.next()
      }
    }
  }
}
