package com.humbletrader.tlv.ops

import com.humbletrader.tlv.data.{ContourGroup, Contour, ToleranceConfig}

/**
 * operations for groups
 */
trait GroupOps {

  /**
   * check if the given rectangle is close enough horizontally to the group
   * @param rect
   * @param group
   * @param conf
   * @return
   */
  def isRectHorizClose(rect: Contour, group: ContourGroup)
                      (implicit conf: ToleranceConfig) : Boolean = {
    val groupRectangle = group.boundaries
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
  def isRectVertClose(rect: Contour, group: ContourGroup)(implicit conf: ToleranceConfig) : Boolean = {
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
    isRectHorizClose(rect, group) && isRectVertClose(rect, group)


}
