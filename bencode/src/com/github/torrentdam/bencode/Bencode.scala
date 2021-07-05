package com.github.torrentdam.bencode

import scodec.bits.ByteVector

import scala.collection.immutable.ListMap

enum Bencode:
  case BString(value: ByteVector)
  case BInteger(value: Long)
  case BList(values: List[Bencode])
  case BDictionary(values: ListMap[String, Bencode])

object Bencode {

  object BString {
    def apply(string: String): BString =
      new BString(ByteVector.encodeUtf8(string).toOption.get)
    val Empty = new BString(ByteVector.empty)
  }

  object BDictionary {
    def apply(values: (String, Bencode)*): BDictionary = new BDictionary(values.to(ListMap))
    val Empty: BDictionary = new BDictionary(ListMap.empty)
  }
}
