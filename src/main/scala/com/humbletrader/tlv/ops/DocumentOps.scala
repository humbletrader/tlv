package com.humbletrader.tlv.ops

import com.humbletrader.tlv.data.{Group, Rectangle, ToleranceConfig, ScannedDocument}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
 * document operations
 */
trait DocumentOps extends GroupOps with RectangleOps {

  /**
   * finds the closest groups to the given rectangle
   * @param rect
   * @param groups
   * @param config
   * @return
   */
  def findCloseGroups(rect: Rectangle, groups: collection.Set[Group])
                     (implicit config: ToleranceConfig) : Set[Group] = {
    groups.foldLeft(ArrayBuffer.empty[Group]){(agg, group) =>
        if(isRectangleClose(rect, group)) agg += group
        else agg
      }
      .toSet
  }

  /**
   * adds a new contour to the given document
   * @param rect
   * @param document
   * @param config
   * @return
   */
  def addContour(rect: Rectangle, document: ScannedDocument)
                (implicit config: ToleranceConfig) : ScannedDocument = {

    var expandingRectangle = rect
    val resultGroups = mutable.HashSet[Group](document.groups: _*) //todo : change this
    var closeEnoughGroups : Set[Group] = Set.empty

    do{
      closeEnoughGroups = findCloseGroups(expandingRectangle, resultGroups)
      //expand the current rectangle to include the close enough groups
      expandingRectangle = computeEnclosingRectangle(
        //todo: change this append
        Set(expandingRectangle) ++ closeEnoughGroups.map(_.boundaries)
      )

      //remove groups which are close enough to our contour/rectangle
      closeEnoughGroups.foreach { g => resultGroups.remove(g)}

    }while(closeEnoughGroups.nonEmpty && resultGroups.nonEmpty)

    //todo: improve below
    ScannedDocument(resultGroups.toSeq ++ Seq(Group("merged group", expandingRectangle)))
  }


}
