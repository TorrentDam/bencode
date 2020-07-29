package com.github.torrentdam.bencode.reader

import verify._
import com.github.torrentdam.bencode.Bencode
import com.github.torrentdam.bencode.format.BencodeFormat

object BencodeFormatSpec extends BasicTestSuite {

  test("decode list") {
    val input = Bencode.BList(
      Bencode.BString("a") ::
        Bencode.BString("b") :: Nil
    )
    val listStringReader: BencodeFormat[List[String]] = implicitly

    assert(listStringReader.read(input) == Right(List("a", "b")))
  }

}
