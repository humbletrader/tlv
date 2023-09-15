package com.humbletrader.tlv.template.detector

import com.humbletrader.tlv.data.{Group, Rectangle}
import com.humbletrader.tlv.detector.GroupDetector
import com.humbletrader.tlv.template.data.Template

/**
 * group detection based on a template
 * @param template
 */
class TemplateBasedGroupDetector(template: Template) extends GroupDetector{

  override def detectGroupFor(rect: Rectangle): Option[Group] = {
    println(s"detecting group for rectangle $rect")
    None
  }
}
