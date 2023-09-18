package com.humbletrader.tlv.ops

import com.humbletrader.tlv.data.{Group, Rectangle, ScanConfig, ScannedDocument}
import org.scalatest.FunSuite

class DocumentOpsTest extends FunSuite{

  private val underTest = new DocumentOps{}

  test("find close enough groups"){
    implicit val config : ScanConfig = ScanConfig(1)

    val document = Set(
      Group("close on left", Rectangle(0, 0, 99, 99)),
      Group("noFriend", Rectangle(0, 100, 98, 199)),
      Group("close on right", Rectangle(201, 1, 400, 2))
    )

    val closeEnoughGroups = underTest.findCloseGroups(Rectangle(100,100, 200, 200), document)

    assert(closeEnoughGroups.map(_.name) == Set("close on left", "close on right"))
  }

  implicit val config : ScanConfig = ScanConfig(1)

  test("adding contour on empty document"){
    val resultDocument = underTest.add(Rectangle(100,100,200,200), ScannedDocument(Seq.empty))
    assert(resultDocument.groups.map(_.boundaries) == Seq(Rectangle(100, 100, 200, 200)))
  }

  test("adding contour over document with far away groups") {
    val contour = Rectangle(100, 100, 200, 200)
    val currentDocument = ScannedDocument(Seq(
      Group("footer", Rectangle(0, 900, 1000, 1000))
    ))

    val resultDocument = underTest.add(contour, currentDocument)
    assert(resultDocument.groups.map(_.boundaries).toSet == Set(Rectangle(100, 100, 200, 200), Rectangle(0, 900, 1000, 1000)))
  }

  test("adding contour over document with one close enough group") {
    val contour = Rectangle(201, 50, 300, 70)
    val currentDocument = ScannedDocument(Seq(
      Group("header", Rectangle(0, 0, 200, 100))
    ))

    val resultDocument = underTest.add(contour, currentDocument)
    assert(resultDocument.groups.map(_.boundaries).toSet == Set(Rectangle(0, 0, 300, 100)))
  }

  /**
   * before : |first| <---big gap ---> |second|
   *
   * after :  |first|contour|second|
   */
  test("add contour close to two groups (on on left and another one on right)") {

    val currentDocument = ScannedDocument(Seq(
      Group("first", Rectangle(0, 0, 200, 100)),
      // there's gap between 200 and 500
      Group("second", Rectangle(500, 0, 700, 100))
    ))

    val contour = Rectangle(201, 50, 499, 70)
    val resultDocument = underTest.add(contour, currentDocument)
    assert(resultDocument.groups.map(_.boundaries).toSet == Set(Rectangle(0, 0, 700, 100)))
  }

  /**
   *    before : |first| <---big gap ---> |second|
   *
   *    after :  |first|contour|second|
   */
  test("add contour close to a group and the expanded rectangle becomes close to another group") {

    val currentDocument = ScannedDocument(Seq(
      Group("first", Rectangle(0, 0, 200, 100)),
      // there's gap between 200 and 500
      Group("second", Rectangle(500, 0, 700, 100))
    ))

    val contour = Rectangle(201, 50, 499, 70)
    val resultDocument = underTest.add(contour, currentDocument)
    assert(resultDocument.groups.map(_.boundaries).toSet == Set(Rectangle(0, 0, 700, 100)))
  }

}
