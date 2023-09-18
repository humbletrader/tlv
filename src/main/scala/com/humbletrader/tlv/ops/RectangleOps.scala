package com.humbletrader.tlv.ops

import com.humbletrader.tlv.data.{Point, Rectangle}

trait RectangleOps {

  def enclosingRectangle(rectangles: Set[Rectangle]) : Rectangle = {

    val (upperLeftX, upperLeftY, lowerRightX, lowerRightY) =
      rectangles.map(rect =>
        (rect.upperLeft.x, rect.upperLeft.y, rect.lowerRight.x, rect.lowerRight.y)
      )
      .reduce{(agg, values) =>
        val (minUlx, minUly, maxLrx, maxLry) = agg
        val (ulx, uly, lrx, lry) = values
        (minUlx min ulx, minUly min uly, maxLrx max lrx, maxLry max lry)
      }

    Rectangle(upperLeftX , upperLeftY, lowerRightX, lowerRightY)
  }

}
