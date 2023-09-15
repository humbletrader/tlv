package com.humbletrader.tlv.ops

import com.humbletrader.tlv.data.{Group, Rectangle}

/**
 * operations for groups
 */
trait GroupOps {

  /**
   * detects if given rectangle is inside the group
   * @param rect
   * @param group
   * @return
   */
  def isRectangleInGroup(rect: Rectangle, group: Group) : Boolean = {
    val groupUpperLeftBound = group.boundaries.upperLeft
    val groupLowerRightBound = group.boundaries.lowerRight

    groupUpperLeftBound.x <= rect.upperLeft.x &&
      groupUpperLeftBound.y <= rect.upperLeft.y &&
      groupLowerRightBound.x >= rect.lowerRight.x &&
      groupLowerRightBound.y >= rect.lowerRight.y
  }

}
