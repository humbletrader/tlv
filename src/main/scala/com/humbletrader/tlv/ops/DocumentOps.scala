package com.humbletrader.tlv.ops

import com.humbletrader.tlv.data.{Group, Rectangle, ScanConfig, ScannedDocument}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer


trait DocumentOps extends GroupOps with RectangleOps {

  def findCloseGroups(rect: Rectangle, groups: collection.Set[Group])
                     (implicit config: ScanConfig) : Set[Group] = {
    groups
      .foldLeft(ArrayBuffer.empty[Group]){(agg, group) =>
        if(isRectangleCloseOnRight(rect, group) ||
          isRectangleCloseOnLeft(rect, group) ||
          isRectangleCloseAbove(rect, group)
        ) agg += group
        else agg
      }
      .toSet
  }

  def add(rect: Rectangle, document: ScannedDocument)
         (implicit config: ScanConfig) : ScannedDocument = {

    var expandingRectangle = rect
    val resultGroups = mutable.HashSet[Group](document.groups: _*) //todo : change this
    var closeEnoughGroups : Set[Group] = Set.empty

    do{
      closeEnoughGroups = findCloseGroups(expandingRectangle, resultGroups)
      //expand the current rectangle to include the close enough groups
      expandingRectangle = enclosingRectangle(
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
