package com.humbletrader.tlv.data

object ScannedDocument{
  def empty() : ScannedDocument = ScannedDocument(Set.empty)
}

case class ScannedDocument(groups: collection.Set[ContourGroup]) {
  override def toString: String =
    s"""
      | ScannedDocument(
      | ${groups}
      |)
      |""".stripMargin
}
