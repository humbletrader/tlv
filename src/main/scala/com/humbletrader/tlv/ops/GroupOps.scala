package com.humbletrader.tlv.ops

import com.humbletrader.tlv.data.{Group, Rectangle, ScanConfig}

/**
 * operations for groups
 */
trait GroupOps {

  /**
   * the distance between the right edge of the group and the left edge of the rectangle
   * @param rect
   * @param group
   * @return
   */
  def horizDistanceOnRight(rect: Rectangle, group: Group) : Int =
    (rect.upperLeft.x - group.boundaries.lowerRight.x).abs


  /**
   * the distance between the left edge of the group and the right edge of the rectangle
   * @param rect
   * @param group
   * @return
   */
  def horizDistanceOnLeft(rect: Rectangle, group: Group) : Int =
    (rect.lowerRight.x - group.boundaries.upperLeft.x).abs


  def verticalDistanceUp(rect: Rectangle, group: Group) : Int =
    (rect.lowerRight.y - group.boundaries.upperLeft.y).abs

  def verticalDistanceDown(rect: Rectangle, group: Group): Int =
    (rect.upperLeft.y - group.boundaries.lowerRight.y).abs


  /**
   * returns true if the given rectangle is on the right side of the group
   * @param rect
   * @param group
   * @return
   */
  def isRectangleOnRight(rect: Rectangle, group: Group) : Boolean =
    group.boundaries.upperLeft.x < rect.lowerRight.x


  def isRectangleOnLeft(rect: Rectangle, group: Group) : Boolean =
    group.boundaries.lowerRight.x > rect.upperLeft.x


  def isRectangleAbove(rect: Rectangle, group: Group) : Boolean =
    rect.lowerRight.y <= group.boundaries.upperLeft.y

  def isRectangleBelow(rect: Rectangle, group: Group) : Boolean =
    rect.upperLeft.y >= group.boundaries.lowerRight.y

  /**
   * returns true if the rectangle is close enough to the right edge of the group
   * @param rect
   * @param group
   * @param conf
   * @return
   */
  def isRectangleCloseOnRight(rect: Rectangle, group: Group)
                             (implicit conf: ScanConfig) : Boolean =
    isRectangleOnRight(rect, group) && horizDistanceOnRight(rect, group) <= conf.errorMargin


  def isRectangleCloseOnLeft(rect: Rectangle, group: Group)
                            (implicit conf: ScanConfig) : Boolean =
    isRectangleOnLeft(rect, group) && horizDistanceOnLeft(rect, group) <= conf.errorMargin

  def isRectangleCloseAbove(rect: Rectangle, group: Group)
                           (implicit conf: ScanConfig) : Boolean =
    isRectangleAbove(rect, group) && verticalDistanceUp(rect, group) <= conf.errorMargin

  def isRectangleHorizontallyClose(rect: Rectangle, group: Group)
                                  (implicit conf: ScanConfig) : Boolean = {
    val horizontalDistance = if (isRectangleOnRight(rect, group)) horizDistanceOnRight(rect, group)
    else if (isRectangleOnLeft(rect, group)) horizDistanceOnLeft(rect, group)
    else throw new IllegalArgumentException("for the sake of simplicity we don't allow overlapping contours")

    horizontalDistance <= conf.errorMargin
  }

  def isRectHorizClose(rect: Rectangle, group: Group)(implicit conf: ScanConfig) : Boolean = {
    val groupRectangle = group.boundaries
    val groupX = groupRectangle.upperLeft.x + groupRectangle.lowerRight.x
    val rectX = rect.upperLeft.x + rect.lowerRight.x

    (groupX - rectX).abs <= groupRectangle.length + rect.length + 2 * conf.errorMargin
  }

  def isRectangleVerticallyClose(rect: Rectangle, group: Group)
                                (implicit conf: ScanConfig) : Boolean = {
    val verticalDistance = if (isRectangleAbove(rect, group)) verticalDistanceUp(rect, group)
    else if (isRectangleBelow(rect, group)) verticalDistanceDown(rect, group)
    else throw new IllegalArgumentException("for the sake of simplicity we don't allow overlapping contours")
    verticalDistance <= conf.errorMargin
  }


  def isRectangleClose(rect: Rectangle, group: Group)
                      (implicit conf: ScanConfig) : Boolean =
    isRectangleHorizontallyClose(rect, group) && isRectangleVerticallyClose(rect, group)


}
