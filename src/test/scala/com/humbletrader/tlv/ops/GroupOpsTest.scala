package com.humbletrader.tlv.ops

import org.scalatest.FunSuite
import com.humbletrader.tlv.data.{Group, Point, Rectangle, ScanConfig}

class GroupOpsTest extends FunSuite{

  val underTest = new GroupOps{}

  test("isRectangleInGroup: same rectangle as group boundaries is inside group"){
    val groupRectangle = Rectangle(Point(100, 100), Point(200,200))
    val group = Group("test group", groupRectangle)

    assert(
      underTest.isRectangleInGroup(groupRectangle, group) === true
    )
  }

  test("isRectangleInGroup: rectangle outside group boundaries on upper side ") {
    val groupRectangle = Rectangle(Point(100, 100), Point(200, 200))
    val group = Group("test group", groupRectangle)
    val testRectangle = Rectangle(Point(0, 100), Point(200, 200))
    assert(
      underTest.isRectangleInGroup(testRectangle, group) === false
    )
  }

  test("isRectangleInGroup: rectangle outside group boundaries on left side ") {
    val groupRectangle = Rectangle(Point(100, 100), Point(200, 200))
    val group = Group("test group", groupRectangle)
    val testRectangle = Rectangle(Point(100, 90), Point(200, 200))
    assert(
      underTest.isRectangleInGroup(testRectangle, group) === false
    )
  }

  test("isRectangleInGroup: rectangle outside group boundaries on right side ") {
    val groupRectangle = Rectangle(Point(100, 100), Point(200, 200))
    val group = Group("test group", groupRectangle)
    val testRectangle = Rectangle(Point(100, 100), Point(201, 200))
    assert(
      underTest.isRectangleInGroup(testRectangle, group) === false
    )
  }

  test("isRectangleInGroup: rectangle outside group boundaries on lower side ") {
    val groupRectangle = Rectangle(Point(100, 100), Point(200, 200))
    val group = Group("test group", groupRectangle)
    val testRectangle = Rectangle(Point(100, 100), Point(200, 201))
    assert(
      underTest.isRectangleInGroup(testRectangle, group) === false
    )
  }

  test("isRectangleInGroup: rectangle completely outside group ") {
    val groupRectangle = Rectangle(Point(100, 100), Point(200, 200))
    val group = Group("test group", groupRectangle)
    val testRectangle = Rectangle(Point(100, 300), Point(200, 400))
    assert(
      underTest.isRectangleInGroup(testRectangle, group) === false
    )
  }

  test("isRectangleOnRight"){
    val groupRectangle = Rectangle(100, 100, 200, 200)
    val group = Group("test group", groupRectangle)
    val rectangleOnTheRightSideOfGroup = Rectangle(250, 100, 350, 200)
    assert(
      underTest.isRectangleOnRight(rectangleOnTheRightSideOfGroup, group)
    )
    val rectangleOnTheLeftSide = Rectangle(0,0, 100, 100)
    assert(
      underTest.isRectangleOnRight(rectangleOnTheLeftSide, group) == false
    )
    val rectangleInsideTheGroup = Rectangle(101, 101, 199, 199)
    assert(
      underTest.isRectangleOnRight(rectangleInsideTheGroup, group) == false
    )
    val rectangleIntersectedWithGroup = Rectangle(150, 150, 300, 300)
    assert(
      underTest.isRectangleOnRight(rectangleIntersectedWithGroup, group) == false
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
      underTest.distanceOnTheLeft(rectangleOnTheLeftSideOfGroup, group) == 1
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


  test("isRectangleCloseOnLeft") {
    val groupRectangle = Rectangle(100, 100, 200, 200)
    val group = Group("test group", groupRectangle)

    val rectangleFarOnRight = Rectangle(202, 100, 350, 200)
    assert(
      underTest.isRectangleCloseOnLeft(rectangleFarOnRight, group) == false
    )

    val rectangleClose = Rectangle(0, 0, 99, 500)
    assert(
      underTest.isRectangleCloseOnLeft(rectangleClose, group) == true
    )

    val rectangleFarOnLeft = Rectangle(0, 0, 198, 200)
    assert(
      underTest.isRectangleCloseOnLeft(rectangleFarOnLeft, group) == false
    )
  }
}
