package com.humbletrader.tlv.ops

import com.humbletrader.tlv.data.Contour

trait ContourOps {

  /**
   * computes the coordinates of a new contour rectangle which contains all given set of rectangles.
   * The enclosing rectangle is computed as
   *   result.upperLeft.x = min(rectangles.upperLeft.x)
   *   result.upperLeft.y = min(rectangles.upperLeft.y)
   *   result.lowerRight.x = max(rectangles.lowerRight.x)
   *   result.lowerRight.y = max(rectangles.lowerRight.y)
   *
   * @param rectangles
   * @return
   */
  def computeEnclosingRectangle(rectangles: Set[Contour]) : Contour = {
    val (upperLeftX, upperLeftY, lowerRightX, lowerRightY) =
      rectangles.map(rect =>
        (rect.upperLeft.x, rect.upperLeft.y, rect.lowerRight.x, rect.lowerRight.y)
      ).reduce{(agg, newValue) =>
        val (minUlx, minUly, maxLrx, maxLry) = agg
        val (ulx, uly, lrx, lry) = newValue
        (minUlx min ulx, minUly min uly, maxLrx max lrx, maxLry max lry)
      }

    Contour(upperLeftX , upperLeftY, lowerRightX, lowerRightY)
  }

}
