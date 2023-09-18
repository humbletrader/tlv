package com.humbletrader.tlv.ops

import com.humbletrader.tlv.data.{Group, Rectangle, ScanConfig}

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

  /**
   * the distance between the right edge of the group and the left edge of the rectangle
   * @param rect
   * @param group
   * @return
   */
  def distanceOnTheRight(rect: Rectangle, group: Group) : Int = {
    (rect.upperLeft.x - group.boundaries.lowerRight.x).abs
  }

  /**
   * the distance between the left edge of the group and the right edge of the rectangle
   * @param rect
   * @param group
   * @return
   */
  def distanceOnTheLeft(rect: Rectangle, group: Group) : Int = {
    (rect.lowerRight.x - group.boundaries.upperLeft.x).abs
  }

  /**
   * returns true if the given rectangle is on the right side of the group
   * @param rect
   * @param group
   * @return
   */
  def isRectangleOnRight(rect: Rectangle, group: Group) : Boolean = {
    rect.upperLeft.x >= group.boundaries.lowerRight.x
  }

  def isRectangleOnLeft(rect: Rectangle, group: Group) : Boolean = {
    rect.lowerRight.x <= group.boundaries.upperLeft.x
  }

  /**
   * returns true if the rectangle is close enough to the right edge of the group
   * @param rect
   * @param group
   * @param conf
   * @return
   */
  def isRectangleCloseOnRight(rect: Rectangle, group: Group)
                             (implicit conf: ScanConfig) : Boolean = {
    isRectangleOnRight(rect, group) && distanceOnTheRight(rect, group) <= conf.errorMargin
  }

  def isRectangleCloseOnLeft(rect: Rectangle, group: Group)(implicit conf: ScanConfig) : Boolean =
    isRectangleOnLeft(rect, group) && distanceOnTheLeft(rect, group) <= conf.errorMargin

}
