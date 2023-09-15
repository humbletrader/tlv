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
    false
  }

}
