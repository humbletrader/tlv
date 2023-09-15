package com.humbletrader.tlv.ops

import org.scalatest.FunSuite
import com.humbletrader.tlv.data.{Rectangle, Point, Group}

class GroupOpsTest extends FunSuite{

  val underTest = new GroupOps{}

  test("same rectangle as group boundaries is inside group"){
    val groupRectangle = Rectangle(Point(100, 100), Point(200,200))
    val group = Group("test group", groupRectangle)

    assert(
      underTest.isRectangleInGroup(groupRectangle, group) === true
    )
  }

  test("rectangle outside group boundaries on upper side ") {
    val groupRectangle = Rectangle(Point(100, 100), Point(200, 200))
    val group = Group("test group", groupRectangle)
    val testRectangle = Rectangle(Point(0, 100), Point(200, 200))
    assert(
      underTest.isRectangleInGroup(testRectangle, group) === false
    )
  }

  test("rectangle outside group boundaries on left side ") {
    val groupRectangle = Rectangle(Point(100, 100), Point(200, 200))
    val group = Group("test group", groupRectangle)
    val testRectangle = Rectangle(Point(100, 90), Point(200, 200))
    assert(
      underTest.isRectangleInGroup(testRectangle, group) === false
    )
  }

  test("rectangle outside group boundaries on right side ") {
    val groupRectangle = Rectangle(Point(100, 100), Point(200, 200))
    val group = Group("test group", groupRectangle)
    val testRectangle = Rectangle(Point(100, 100), Point(201, 200))
    assert(
      underTest.isRectangleInGroup(testRectangle, group) === false
    )
  }

  test("rectangle outside group boundaries on lower side ") {
    val groupRectangle = Rectangle(Point(100, 100), Point(200, 200))
    val group = Group("test group", groupRectangle)
    val testRectangle = Rectangle(Point(100, 100), Point(200, 201))
    assert(
      underTest.isRectangleInGroup(testRectangle, group) === false
    )
  }

  test("rectangle completely outside group ") {
    val groupRectangle = Rectangle(Point(100, 100), Point(200, 200))
    val group = Group("test group", groupRectangle)
    val testRectangle = Rectangle(Point(100, 300), Point(200, 400))
    assert(
      underTest.isRectangleInGroup(testRectangle, group) === false
    )
  }

}
