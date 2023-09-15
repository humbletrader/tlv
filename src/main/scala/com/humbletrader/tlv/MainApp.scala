package com.humbletrader.tlv

import com.humbletrader.tlv.data.Rectangle
import com.humbletrader.tlv.data.Point
import com.humbletrader.tlv.template.data.Template
import com.humbletrader.tlv.template.detector.TemplateBasedGroupDetector

object MainApp {

  def main(args: Array[String]): Unit = {

    //todo: read input rectangles
    val inputRectangles = Seq(
      Rectangle(Point(100,100), Point(200,200))
    )

    //todo: read bulletin template
    val bulletinTemplate = Template(groups = Seq.empty)

    //detect groups for input rectangles
    val groupDetector = new TemplateBasedGroupDetector(bulletinTemplate)
    val rectangleAndGroup = inputRectangles.map{rect =>
      val groupName = groupDetector.detectGroupFor(rect) match {
        case Some(group) => group.name
        case None => "no group found"
      }
      groupName -> rect
    }

    //print solution
    println(rectangleAndGroup)
  }

}
