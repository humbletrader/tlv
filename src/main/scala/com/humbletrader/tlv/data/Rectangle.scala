package com.humbletrader.tlv.data

case class Rectangle(upperLeft: Point, lowerRight:Point){
  //todo: validate that lowerRight is lower than upperLeft
  //todo: validate that lowerRight is on the right side of upper left
}
