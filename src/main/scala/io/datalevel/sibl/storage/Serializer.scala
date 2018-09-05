package io.datalevel.sibl.storage

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, ObjectInputStream, ObjectOutputStream}

import io.datalevel.sibl.loan

sealed trait Serializer[A] {
  def toBinary(data: A): Array[Byte]
  def fromBinary(bytes: Array[Byte]): A
}

class DefaultSerializer[A] extends Serializer[A] {
  def toBinary(data: A): Array[Byte] = {
    loan(new ByteArrayOutputStream()) { stream =>
      loan(new ObjectOutputStream(stream)) { oos =>
        oos.writeObject(data)
        stream.toByteArray
      }
    }
  }

  def fromBinary(bytes: Array[Byte]): A = {
    loan(new ByteArrayInputStream(bytes)) { stream =>
      loan(new ObjectInputStream(stream)) { ois =>
        ois.readObject.asInstanceOf[A]
      }
    }
  }
}
