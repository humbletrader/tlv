package com.humbletrader.tlv.ops

import org.scalatest.FunSuite
import com.humbletrader.tlv.data.{Group, Point, Rectangle, ScanConfig}

class GroupOpsTest extends FunSuite{

  val underTest = new GroupOps{}

  test("check if rectangle is on the right side of the group"){
    val groupRectangle = Rectangle(100, 100, 200, 200)
    val group = Group("test group", groupRectangle)
    val rectangleOnTheRightSideOfGroup = Rectangle(250, 100, 350, 200)
    assert(
      underTest.isRectangleOnRight(rectangleOnTheRightSideOfGroup, group)
    )
    val rectangleOnTheLeftSide = Rectangle(0,0, 100, 100)
    assert(
      !underTest.isRectangleOnRight(rectangleOnTheLeftSide, group)
    )
    val rectangleInsideTheGroup = Rectangle(101, 101, 199, 199)
    assert(
      underTest.isRectangleOnRight(rectangleInsideTheGroup, group)
    )
    val rectangleOverlappingTheGroup = Rectangle(150, 150, 300, 300)
    assert(
      underTest.isRectangleOnRight(rectangleOverlappingTheGroup, group)
    )
  }


  test("isOnLeft") {
    val groupRectangle = Rectangle(100, 100, 200, 200)
    val group = Group("test group", groupRectangle)
    val rectangleOnTheLeftSideOfGroup = Rectangle(0, 0, 99, 99)
    assert(
      underTest.isRectangleOnLeft(rectangleOnTheLeftSideOfGroup, group) == true
    )

    val rectangleOnTheRightSide = Rectangle(201, 0, 300, 300)
    assert(
      underTest.isRectangleOnLeft(rectangleOnTheRightSide, group) == false
    )
  }

  test("distanceOnLeft"){
    val groupRectangle = Rectangle(100, 100, 200, 200)
    val group = Group("test group", groupRectangle)
    val rectangleOnTheLeftSideOfGroup = Rectangle(0, 0, 99, 99)
    assert(
      underTest.horizDistanceOnLeft(rectangleOnTheLeftSideOfGroup, group) == 1
    )
  }

  test("distanceAbove") {
    val groupRectangle = Rectangle(100, 100, 200, 200)
    val group = Group("test group", groupRectangle)
    val rectangleAbove = Rectangle(0, 0, 99, 99)
    assert(
      underTest.horizDistanceOnLeft(rectangleAbove, group) == 1
    )

    val rectangleFarAbove = Rectangle(0,0, 50, 50)
    assert(
      underTest.verticalDistanceUp(rectangleFarAbove, group) == 50
    )
  }

  test("isRectangleAboveGroup") {
    val groupRectangle = Rectangle(100, 100, 200, 200)
    val group = Group("test group", groupRectangle)
    val rectangleCloseAbove = Rectangle(0, 0, 99, 99)
    assert(
      underTest.isRectangleOnLeft(rectangleCloseAbove, group) == true
    )

    val rectangleOnTheRightSide = Rectangle(201, 0, 300, 300)
    assert(
      underTest.isRectangleOnLeft(rectangleOnTheRightSide, group) == false
    )

    val rectangleFarAbove = Rectangle(0, 0, 50, 50)
    assert(
      underTest.isRectangleAbove(rectangleFarAbove, group) == true
    )

    val rectangleOnTheRight = Rectangle(201, 300, 500, 400)
    assert(
      underTest.isRectangleAbove(rectangleOnTheRight, group) == false
    )
  }

  implicit val configuration = ScanConfig(1)

  test("isRectangleCloseOnRight"){
    val groupRectangle = Rectangle(100, 100, 200, 200)
    val group = Group("test group", groupRectangle)

    val rectangleFarOnRight = Rectangle(202, 100, 350, 200)
    assert(
      underTest.isRectangleCloseOnRight(rectangleFarOnRight, group) == false
    )

    val rectangleClose = Rectangle(201, 100, 300, 200)
    assert(
      underTest.isRectangleCloseOnRight(rectangleClose, group) == true
    )

    val rectangleOnLeft = Rectangle(0, 0, 200, 200)
    assert(
      underTest.isRectangleCloseOnRight(rectangleOnLeft, group) == false
    )
  }


  test("rectangle horizontally close to the group") {
    val groupRectangle = Rectangle(100, 100, 200, 200)
    val group = Group("test group", groupRectangle)

    val rectangleFarOnRight = Rectangle(202, 100, 350, 200)
    assert(
      underTest.isRectHorizClose(rectangleFarOnRight, group) == false
    )

    val rectangleClose = Rectangle(0, 0, 99, 500)
    assert(
      underTest.isRectHorizClose(rectangleClose, group) == true
    )

    val rectangleUpLeft = Rectangle(0, 0, 198, 99)
    assert(
      underTest.isRectHorizClose(rectangleUpLeft, group) == true
    )
  }
}
