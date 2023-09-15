package com.humbletrader.tlv.template.detector

import org.scalatest.FunSuite

import com.humbletrader.tlv.data.{Group, Rectangle, Point}
import com.humbletrader.tlv.template.data.Template

class TemplateBasedGroupDetectorText extends FunSuite{

  private val template = Template(Seq(
    Group("header", Rectangle( Point(0, 0), Point(1000, 50))),
    Group("main", Rectangle( Point(0, 51), Point(1000, 500))),
    Group("footer", Rectangle(Point(0, 501), Point(1000, 600)))
  ))
  val underTest = new TemplateBasedGroupDetector(template)

  test("group detection"){
    val insideHeaderRect = Rectangle(Point(0, 1), Point(100, 20))
    assert( underTest.detectGroupFor(insideHeaderRect).map(_.name) === Some("header"))

    val insideMainArea = Rectangle(Point(1, 52), Point(1000, 300))
    assert(underTest.detectGroupFor(insideMainArea).map(_.name) === Some("main"))

    val insideFooter = Rectangle(Point(0, 502), Point(1000, 600))
    assert(underTest.detectGroupFor(insideFooter).map(_.name) === Some("footer"))
  }

}
