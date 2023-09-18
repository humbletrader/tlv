package com.humbletrader.tlv.ops

import com.humbletrader.tlv.data.{ContourGroup, Contour, ToleranceConfig, ScannedDocument}
import org.scalatest.FunSuite

class DocumentOpsTest extends FunSuite{

  private val underTest = new DocumentOps{}

  test("find close enough groups"){
    implicit val config : ToleranceConfig = ToleranceConfig(1)

    val document = Set(
      ContourGroup("close on left", Contour(0, 0, 99, 99)),
      ContourGroup("not close", Contour(0, 100, 98, 199)),
      ContourGroup("close on right", Contour(201, 150, 400, 200))
    )

    val closeEnoughGroups = underTest.findCloseGroups(Contour(100, 100, 200, 200), document)

    assert(closeEnoughGroups.map(_.name) == Set("close on left", "close on right"))
  }

  implicit val config : ToleranceConfig = ToleranceConfig(1)

  test("adding contour on empty document"){
    val resultDocument = underTest.addContourToDocument(Contour(100,100,200,200), ScannedDocument.empty())
    assert(resultDocument.groups.map(_.boundaries) == Set(Contour(100, 100, 200, 200)))
  }

  test("adding contour over document with far away groups") {
    val contour = Contour(100, 100, 200, 200)
    val currentDocument = ScannedDocument(Set(
      ContourGroup("footer", Contour(0, 900, 1000, 1000))
    ))

    val resultDocument = underTest.addContourToDocument(contour, currentDocument)
    assert(resultDocument.groups.map(_.boundaries).toSet == Set(Contour(100, 100, 200, 200), Contour(0, 900, 1000, 1000)))
  }

  test("adding contour over document with one close enough group") {
    val contour = Contour(201, 50, 300, 70)
    val currentDocument = ScannedDocument(Set(
      ContourGroup("header", Contour(0, 0, 200, 100))
    ))

    val resultDocument = underTest.addContourToDocument(contour, currentDocument)
    assert(resultDocument.groups.map(_.boundaries) == Set(Contour(0, 0, 300, 100)))
  }

  /**
   * before : |first| <---big gap ---> |second|
   *
   * after :  |first|contour|second|
   */
  test("add contour close to two groups (on on left and another one on right)") {

    val currentDocument = ScannedDocument(Set(
      ContourGroup("first", Contour(0, 0, 200, 100)),
      // there's gap between 200 and 500
      ContourGroup("second", Contour(500, 0, 700, 100))
    ))

    val contour = Contour(201, 50, 499, 70)
    val resultDocument = underTest.addContourToDocument(contour, currentDocument)
    assert(resultDocument.groups.map(_.boundaries) == Set(Contour(0, 0, 700, 100)))
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
      ContourGroup("first", Contour(0, 0, 200, 100)),
      //there's a horizontal gap here
      ContourGroup("second", Contour(0, 150, 10, 300))
    ))

    val contour = Contour(201, 50, 300, 149)
    val resultDocument = underTest.addContourToDocument(contour, currentDocument)
    assert(resultDocument.groups.map(_.boundaries) == Set(Contour(0, 0, 300, 300)))
  }

}
