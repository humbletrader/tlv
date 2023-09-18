package com.humbletrader.tlv.ops

import org.scalatest.FunSuite
import com.humbletrader.tlv.data.{Group, Rectangle, ScanConfig}

class GroupOpsTest extends FunSuite{

  val underTest = new GroupOps{}

  implicit val configuration = ScanConfig(1)

  test("check rectangle horizontally close to the group") {
    val group = Group("test group", Rectangle(100, 100, 200, 200))

    val rectangleFarOnRight = Rectangle(202, 100, 350, 200)
    assert(!underTest.isRectHorizClose(rectangleFarOnRight, group))
    assert(underTest.isRectHorizClose(rectangleFarOnRight, group)(ScanConfig(2)))

    val rectangleOnePixelAwayOnLeft = Rectangle(0, 0, 99, 50)
    assert(underTest.isRectHorizClose(rectangleOnePixelAwayOnLeft, group))
    assert(!underTest.isRectHorizClose(rectangleOnePixelAwayOnLeft, group)(ScanConfig(0)))

    val overlappingRectangle = Rectangle(0, 0, 198, 99)
    assert(underTest.isRectHorizClose(overlappingRectangle, group))

    val rectangleOnLeftCorner = Rectangle(0, 0, 99, 99)
    assert(underTest.isRectHorizClose(rectangleOnLeftCorner, group))
    assert(!underTest.isRectHorizClose(rectangleOnLeftCorner, group)(ScanConfig(0)))
  }

  test("check rectangle vertically close"){
    val group = Group("test", Rectangle(100, 100, 200, 200))

    val farAboveRect = Rectangle(0,0,10,10)
    assert(!underTest.isRectVertClose(farAboveRect, group))

    val twoPixelsAboveTheTopEdge = Rectangle(0, 0, 10, 98)
    assert(!underTest.isRectVertClose(twoPixelsAboveTheTopEdge, group))

    val onePixelAboveTheTopEdge = Rectangle(0, 0, 10, 99)
    assert(underTest.isRectVertClose(onePixelAboveTheTopEdge, group))
    assert(!underTest.isRectVertClose(onePixelAboveTheTopEdge, group)(ScanConfig(0)))

    val rectangleTouchingTheTopEdgeOfGroup = Rectangle(0, 0, 10, 100)
    assert(underTest.isRectVertClose(rectangleTouchingTheTopEdgeOfGroup, group))
    assert(underTest.isRectVertClose(rectangleTouchingTheTopEdgeOfGroup, group)(ScanConfig(0)))

    val rectangleOverlappingVertically = Rectangle(0, 0, 10, 200)
    assert(underTest.isRectVertClose(rectangleOverlappingVertically, group))

    val rectangleTouchingTheBottomEdge = Rectangle(0, 200, 10, 300)
    assert(underTest.isRectVertClose(rectangleTouchingTheBottomEdge, group))
    assert(underTest.isRectVertClose(rectangleTouchingTheBottomEdge, group)(ScanConfig(0)))

    val rectangleOnePixelBelowTheBottomEdge = Rectangle(0, 201, 10, 300)
    assert(underTest.isRectVertClose(rectangleOnePixelBelowTheBottomEdge, group))
    assert(!underTest.isRectVertClose(rectangleOnePixelBelowTheBottomEdge, group)(ScanConfig(0)))

    val twoPixelsBelowTheBottomEdge = Rectangle(0, 202, 10, 300)
    assert(!underTest.isRectVertClose(twoPixelsBelowTheBottomEdge, group))

    val rectangleOnLeftCorner = Rectangle(0, 0, 99, 99)
    assert(underTest.isRectVertClose(rectangleOnLeftCorner, group))
    assert(!underTest.isRectVertClose(rectangleOnLeftCorner, group)(ScanConfig(0)))
  }
}
