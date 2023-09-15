package com.humbletrader.tlv.ops

import org.scalatest.FunSuite
import com.humbletrader.tlv.data.{Rectangle, Point, Group}

class GroupOpsTest extends FunSuite{

  test("rectangle is in group"){
    val underTest = new GroupOps{}
    val groupRectangle = Rectangle(Point(100, 100), Point(200,200))
    val group = Group("test group", groupRectangle)

    assert(
      underTest.isRectangleInGroup(groupRectangle, group) === true
    )
  }

}
