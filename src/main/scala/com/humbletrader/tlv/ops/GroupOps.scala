package com.humbletrader.tlv.ops

import com.humbletrader.tlv.data.{Group, Rectangle, ToleranceConfig}

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
  def isRectHorizClose(rect: Rectangle, group: Group)
                      (implicit conf: ToleranceConfig) : Boolean = {
    val groupRectangle = group.boundaries
    val groupX = groupRectangle.upperLeft.x + groupRectangle.lowerRight.x
    val rectX = rect.upperLeft.x + rect.lowerRight.x

    (groupX - rectX).abs <= groupRectangle.length + rect.length + 2 * conf.tolerance
  }

  /**
   * check if the given rectangle is close enough vertically to the given group
   * @param rect
   * @param group
   * @param conf
   * @return
   */
  def isRectVertClose(rect: Rectangle, group: Group)(implicit conf: ToleranceConfig) : Boolean = {
    val groupRect = group.boundaries
    val groupY = groupRect.upperLeft.y + groupRect.lowerRight.y
    val rectY = rect.upperLeft.y + rect.lowerRight.y
    (groupY - rectY).abs <= groupRect.width + rect.width + 2 * conf.tolerance
  }

  /**
   * check if the given rectangle is close enough to the group
   * @param rect
   * @param group
   * @param conf
   * @return
   */
  def isRectangleClose(rect: Rectangle, group: Group)
                      (implicit conf: ToleranceConfig) : Boolean =
    isRectHorizClose(rect, group) && isRectVertClose(rect, group)


}
