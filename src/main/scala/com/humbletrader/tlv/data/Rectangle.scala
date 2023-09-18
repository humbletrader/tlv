package com.humbletrader.tlv.data


object Rectangle{
  def apply(upperLeftX: Int, upperLeftY: Int, lowerRightX: Int, lowerRightY: Int) : Rectangle =
    Rectangle(
      Point(upperLeftX, upperLeftY),
      Point(lowerRightX, lowerRightY)
    )
}

case class Rectangle(upperLeft: Point, lowerRight:Point){
  //todo: validate that lowerRight is lower than upperLeft
  //todo: validate that lowerRight is on the right side of upper left


}
