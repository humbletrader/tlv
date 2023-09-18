package com.humbletrader.tlv.ops

import com.humbletrader.tlv.data.{ContourGroup, Rectangle, ToleranceConfig, ScannedDocument}
import org.scalatest.FunSuite

class DocumentOpsTest extends FunSuite{

  private val underTest = new DocumentOps{}

  test("find close enough groups"){
    implicit val config : ToleranceConfig = ToleranceConfig(1)

    val document = Set(
      ContourGroup("close on left", Rectangle(0, 0, 99, 99)),
      ContourGroup("not close", Rectangle(0, 100, 98, 199)),
      ContourGroup("close on right", Rectangle(201, 150, 400, 200))
    )

    val closeEnoughGroups = underTest.findCloseGroups(Rectangle(100, 100, 200, 200), document)

    assert(closeEnoughGroups.map(_.name) == Set("close on left", "close on right"))
  }

  implicit val config : ToleranceConfig = ToleranceConfig(1)

  test("adding contour on empty document"){
    val resultDocument = underTest.addContour(Rectangle(100,100,200,200), ScannedDocument.empty())
    assert(resultDocument.groups.map(_.boundaries) == Set(Rectangle(100, 100, 200, 200)))
  }

  test("adding contour over document with far away groups") {
    val contour = Rectangle(100, 100, 200, 200)
    val currentDocument = ScannedDocument(Set(
      ContourGroup("footer", Rectangle(0, 900, 1000, 1000))
    ))

    val resultDocument = underTest.addContour(contour, currentDocument)
    assert(resultDocument.groups.map(_.boundaries).toSet == Set(Rectangle(100, 100, 200, 200), Rectangle(0, 900, 1000, 1000)))
  }

  test("adding contour over document with one close enough group") {
    val contour = Rectangle(201, 50, 300, 70)
    val currentDocument = ScannedDocument(Set(
      ContourGroup("header", Rectangle(0, 0, 200, 100))
    ))

    val resultDocument = underTest.addContour(contour, currentDocument)
    assert(resultDocument.groups.map(_.boundaries) == Set(Rectangle(0, 0, 300, 100)))
  }

  /**
   * before : |first| <---big gap ---> |second|
   *
   * after :  |first|contour|second|
   */
  test("add contour close to two groups (on on left and another one on right)") {

    val currentDocument = ScannedDocument(Set(
      ContourGroup("first", Rectangle(0, 0, 200, 100)),
      // there's gap between 200 and 500
      ContourGroup("second", Rectangle(500, 0, 700, 100))
    ))

    val contour = Rectangle(201, 50, 499, 70)
    val resultDocument = underTest.addContour(contour, currentDocument)
    assert(resultDocument.groups.map(_.boundaries) == Set(Rectangle(0, 0, 700, 100)))
  }

  /**
   *    before : |first|
   *                |
   *               gap
   *                |
   *             |second|
   *
   *    after :  |first|contour
   *             |second|
   */
  test("add contour close to a group and the expanded rectangle becomes close to another group") {

    val currentDocument = ScannedDocument(Set(
      ContourGroup("first", Rectangle(0, 0, 200, 100)),
      //there's a horizontal gap here
      ContourGroup("second", Rectangle(0, 150, 10, 300))
    ))

    val contour = Rectangle(201, 50, 300, 149)
    val resultDocument = underTest.addContour(contour, currentDocument)
    assert(resultDocument.groups.map(_.boundaries) == Set(Rectangle(0, 0, 300, 300)))
  }

}
