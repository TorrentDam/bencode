package com.github.torrentdam.bencode

import scodec.bits.ByteVector

import scala.collection.immutable.ListMap

sealed trait Bencode

object Bencode {

  case class BString(value: ByteVector) extends Bencode
  case class BInteger(value: Long) extends Bencode
  case class BList(values: List[Bencode]) extends Bencode
  case class BDictionary(values: ListMap[String, Bencode]) extends Bencode

  object BString {
    def apply(string: String): BString =
      new BString(ByteVector.encodeUtf8(string).right.get)
    val Empty = new BString(ByteVector.empty)
  }

  object BDictionary {
    def apply(values: (String, Bencode)*): BDictionary = new BDictionary(values.to(ListMap))
    val Empty: BDictionary = new BDictionary(ListMap.empty)
  }
}
