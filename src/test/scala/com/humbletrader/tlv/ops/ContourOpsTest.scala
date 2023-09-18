package com.humbletrader.tlv.ops

import com.humbletrader.tlv.data.Contour
import org.scalatest.FunSuite

class ContourOpsTest extends FunSuite{

  private val underTest = new ContourOps{}

  test("enclosing rectangles when they don't overlap"){
    val enclosingRectangle = underTest.computeEnclosingRectangle(Set(
      Contour(0, 0, 100, 100),
      Contour(200, 200, 300, 300)
    ))

    assert(enclosingRectangle == Contour(0,0,300,300))
  }

  test("enclosing rectangles when they overlap"){
    val enclosing = underTest.computeEnclosingRectangle(Set(
      Contour(100, 100, 200, 200),
      Contour(110, 110, 250, 250),
      Contour(50, 170, 150, 350)
    ))

    assert(enclosing == Contour(50, 100, 250, 350))
  }

  test("enclosing one single rectangle") {
    val enclosing = underTest.computeEnclosingRectangle(Set(
      Contour(100, 100, 200, 200)
    ))

    assert(enclosing == Contour(100, 100, 200, 200))
  }

}
