package com.humbletrader.tlv.ops

import org.scalatest.FunSuite
import com.humbletrader.tlv.data.{ContourGroup, Contour, ToleranceConfig}

class GroupOpsTest extends FunSuite{

  val underTest = new GroupOps{}

  implicit val configuration = ToleranceConfig(1)

  test("check rectangle horizontally close to the group") {
    val group = ContourGroup("test group", Contour(100, 100, 200, 200))

    val rectangleFarOnRight = Contour(202, 100, 350, 200)
    assert(!underTest.isRectangleHorizClose(rectangleFarOnRight, group))
    assert(underTest.isRectangleHorizClose(rectangleFarOnRight, group)(ToleranceConfig(2)))

    val rectangleOnePixelAwayOnLeft = Contour(0, 0, 99, 50)
    assert(underTest.isRectangleHorizClose(rectangleOnePixelAwayOnLeft, group))
    assert(!underTest.isRectangleHorizClose(rectangleOnePixelAwayOnLeft, group)(ToleranceConfig(0)))

    val overlappingRectangle = Contour(0, 0, 198, 99)
    assert(underTest.isRectangleHorizClose(overlappingRectangle, group))

    val rectangleOnLeftCorner = Contour(0, 0, 99, 99)
    assert(underTest.isRectangleHorizClose(rectangleOnLeftCorner, group))
    assert(!underTest.isRectangleHorizClose(rectangleOnLeftCorner, group)(ToleranceConfig(0)))
  }

  test("check rectangle vertically close"){
    val group = ContourGroup("test", Contour(100, 100, 200, 200))

    val farAboveRect = Contour(0,0,10,10)
    assert(!underTest.isRectangleVertClose(farAboveRect, group))

    val twoPixelsAboveTheTopEdge = Contour(0, 0, 10, 98)
    assert(!underTest.isRectangleVertClose(twoPixelsAboveTheTopEdge, group))

    val onePixelAboveTheTopEdge = Contour(0, 0, 10, 99)
    assert(underTest.isRectangleVertClose(onePixelAboveTheTopEdge, group))
    assert(!underTest.isRectangleVertClose(onePixelAboveTheTopEdge, group)(ToleranceConfig(0)))

    val rectangleTouchingTheTopEdgeOfGroup = Contour(0, 0, 10, 100)
    assert(underTest.isRectangleVertClose(rectangleTouchingTheTopEdgeOfGroup, group))
    assert(underTest.isRectangleVertClose(rectangleTouchingTheTopEdgeOfGroup, group)(ToleranceConfig(0)))

    val rectangleOverlappingVertically = Contour(0, 0, 10, 200)
    assert(underTest.isRectangleVertClose(rectangleOverlappingVertically, group))

    val rectangleTouchingTheBottomEdge = Contour(0, 200, 10, 300)
    assert(underTest.isRectangleVertClose(rectangleTouchingTheBottomEdge, group))
    assert(underTest.isRectangleVertClose(rectangleTouchingTheBottomEdge, group)(ToleranceConfig(0)))

    val rectangleOnePixelBelowTheBottomEdge = Contour(0, 201, 10, 300)
    assert(underTest.isRectangleVertClose(rectangleOnePixelBelowTheBottomEdge, group))
    assert(!underTest.isRectangleVertClose(rectangleOnePixelBelowTheBottomEdge, group)(ToleranceConfig(0)))

    val twoPixelsBelowTheBottomEdge = Contour(0, 202, 10, 300)
    assert(!underTest.isRectangleVertClose(twoPixelsBelowTheBottomEdge, group))

    val rectangleOnLeftCorner = Contour(0, 0, 99, 99)
    assert(underTest.isRectangleVertClose(rectangleOnLeftCorner, group))
    assert(!underTest.isRectangleVertClose(rectangleOnLeftCorner, group)(ToleranceConfig(0)))
  }
}
