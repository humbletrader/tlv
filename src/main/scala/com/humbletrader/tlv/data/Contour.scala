package com.humbletrader.tlv.data


object Contour{
  def apply(upperLeftX: Int, upperLeftY: Int, lowerRightX: Int, lowerRightY: Int) : Contour =
    Contour(
      Point(upperLeftX, upperLeftY),
      Point(lowerRightX, lowerRightY)
    )
}

case class Contour(upperLeft: Point, lowerRight:Point){

  //validate that lowerRight is lower than upperLeft
  assert(lowerRight.y > upperLeft.y, "invalid rectangle: lower right corner is above upper left")
  //validate that lowerRight is on the right side of upper left
  assert(lowerRight.x > upperLeft.x, "invalid rectangle: lower right corner should be on ther right side of upperLeft corner")

  val length : Int = lowerRight.x - upperLeft.x
  val width : Int = lowerRight.y - upperLeft.y


}
