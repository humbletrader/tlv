package com.humbletrader.tlv.ops

import com.humbletrader.tlv.data.{ContourGroup, Contour, ToleranceConfig, ScannedDocument}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
 * document operations
 */
trait DocumentOps extends GroupOps with ContourOps {

  /**
   * finds the closest groups to the given rectangle
   * @param rect
   * @param groups
   * @param config
   * @return
   */
  def findCloseGroups(rect: Contour, groups: collection.Set[ContourGroup])
                     (implicit config: ToleranceConfig) : Set[ContourGroup] = {
    groups.foldLeft(ArrayBuffer.empty[ContourGroup]){ (agg, group) =>
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
  def addContourToDocument(rect: Contour, document: ScannedDocument)
                          (implicit config: ToleranceConfig) : ScannedDocument = {

    var expandingRectangle = rect
    val resultGroups = document.groups.to[collection.mutable.Set]
    var closeEnoughGroups : Set[ContourGroup] = Set.empty

    do{
      closeEnoughGroups = findCloseGroups(expandingRectangle, resultGroups)
      //expand the current rectangle to include the close enough groups
      expandingRectangle = computeEnclosingRectangle(
        //todo: change this append
        Set(expandingRectangle) ++ closeEnoughGroups.map(_.boundaries)
      )

      //remove groups which are close enough to our contour/rectangle
      closeEnoughGroups.foreach { g =>
        //todo: there's an extra iteration here which was kept for readability purposes
        //otherwise the removal of groups should have been included in the findGroups operation
        resultGroups.remove(g)
      }

      //iterate as long as you keep finding groups which are close
    }while(closeEnoughGroups.nonEmpty && resultGroups.nonEmpty)

    ScannedDocument(resultGroups + ContourGroup("merged group", expandingRectangle))
  }
}
