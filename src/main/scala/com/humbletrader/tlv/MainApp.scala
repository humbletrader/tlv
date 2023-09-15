package com.humbletrader.tlv

import com.humbletrader.tlv.data.{Rectangle, Point, Group}
import com.humbletrader.tlv.template.data.Template
import com.humbletrader.tlv.template.detector.TemplateBasedGroupDetector

object MainApp {

  def main(args: Array[String]): Unit = {

    //todo: read input rectangles from command line
    val inputRectangles = Seq(
      Rectangle(Point(100,100), Point(200,200)),
      Rectangle(Point(0, 0), Point(10, 100)),
      Rectangle(Point(10, 10), Point(50, 50))
    )

    //todo: read bulletin template from command line
    val bulletinTemplate = Template(Seq(
      Group("header", Rectangle(Point(0, 0), Point(1000, 50))),
      Group("main", Rectangle(Point(0, 51), Point(1000, 500))),
      Group("footer", Rectangle(Point(0, 501), Point(1000, 600)))
    ))

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
