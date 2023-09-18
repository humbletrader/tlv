package com.humbletrader.tlv.ops

import com.humbletrader.tlv.data.{ContourGroup, Contour, ToleranceConfig}

/**
 * operations for contour groups
 */
trait GroupOps {

  /**
   * check if the given rectangle is close enough horizontally to the group.
   * Note: this may return true even if the rectangle is not close enough vertically to the group
   * @param rect
   * @param group
   * @param conf
   * @return
   */
  def isRectangleHorizClose(rect: Contour, group: ContourGroup)
                           (implicit conf: ToleranceConfig) : Boolean = {
    val groupRectangle = group.boundaries
    //based on the mathematical finding
    // rect1 is close to rect2 if and only if
    //xAxisDistance(center(rect1), center(rect2)) <= (rect1.length + rect2.length )/ 2 + tolerance
    //which translates to
    //ABS(
    //  rect1.upperLeft.x + rect1.lowerRight.x - rect2.upperLeft.x - rect2.lowerRight.x
    //) < rect1.length + rect2.length + 2*tolerance
    val sumXGroup = groupRectangle.upperLeft.x + groupRectangle.lowerRight.x
    val sumXRect = rect.upperLeft.x + rect.lowerRight.x

    (sumXGroup - sumXRect).abs <= groupRectangle.length + rect.length + 2 * conf.tolerance
  }

  /**
   * check if the given rectangle is close enough vertically to the given group
   * @param rect
   * @param group
   * @param conf
   * @return
   */
  def isRectangleVertClose(rect: Contour, group: ContourGroup)
                          (implicit conf: ToleranceConfig) : Boolean = {
    val groupRect = group.boundaries
    val sumYGroup = groupRect.upperLeft.y + groupRect.lowerRight.y
    val sumYRect = rect.upperLeft.y + rect.lowerRight.y
    (sumYGroup - sumYRect).abs <= groupRect.width + rect.width + 2 * conf.tolerance
  }

  /**
   * check if the given rectangle is close enough to the group
   * @param rect
   * @param group
   * @param conf
   * @return
   */
  def isRectangleClose(rect: Contour, group: ContourGroup)
                      (implicit conf: ToleranceConfig) : Boolean =
    isRectangleHorizClose(rect, group) && isRectangleVertClose(rect, group)


}
