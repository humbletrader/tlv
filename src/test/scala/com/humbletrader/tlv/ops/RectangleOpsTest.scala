package com.humbletrader.tlv.ops

import com.humbletrader.tlv.data.Rectangle
import org.scalatest.FunSuite

class RectangleOpsTest extends FunSuite{

  private val underTest = new RectangleOps{}

  test("enclosing rectangles when they don't overlap"){
    val enclosingRectangle = underTest.enclosingRectangle(Set(
      Rectangle(0, 0, 100, 100),
      Rectangle(200, 200, 300, 300)
    ))

    assert(enclosingRectangle == Rectangle(0,0,300,300))
  }

  test("enclosing rectangles when they overlap"){
    val enclosing = underTest.enclosingRectangle(Set(
      Rectangle(100, 100, 200, 200),
      Rectangle(110, 110, 250, 250),
      Rectangle(50, 170, 150, 350)
    ))

    assert(enclosing == Rectangle(50, 100, 250, 350))
  }

  test("enclosing one single rectangle") {
    val enclosing = underTest.enclosingRectangle(Set(
      Rectangle(100, 100, 200, 200)
    ))

    assert(enclosing == Rectangle(100, 100, 200, 200))
  }

}
