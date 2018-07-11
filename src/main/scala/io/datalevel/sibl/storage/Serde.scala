package io.datalevel.sibl.storage

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

import com.sksamuel.avro4s.{AvroInputStream, AvroOutputStream}

sealed trait Serde[A] {
  def serialize(data: A): Array[Byte]
  def deserialize(buffer: Array[Byte]): A
}

class AvroSerde[A] extends Serde[A] {
  def serialize(data: A): Array[Byte] = {
    val baos = new ByteArrayOutputStream()
    val output = AvroOutputStream.binary[A](baos)
    output.write(data)
    output.close()
    baos.toByteArray
  }

  def deserialize(buffer: Array[Byte]): A = {
    val in = new ByteArrayInputStream(buffer)
    val input = AvroInputStream.binary[A](in)
    input.iterator.next()
  }
}
