package com.humbletrader.tlv.data

object ScannedDocument{
  def empty() : ScannedDocument = ScannedDocument(Seq.empty)
}

case class ScannedDocument(groups: Seq[Group]) {
  override def toString: String =
    s"""
      | ScannedDocument(
      | ${groups}
      |)
      |""".stripMargin
}
