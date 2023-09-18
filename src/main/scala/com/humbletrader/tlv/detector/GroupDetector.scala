package com.humbletrader.tlv.detector

import com.humbletrader.tlv.data.{Group, Rectangle}

trait GroupDetector {
  def detectGroupFor(rect: Rectangle) : Option[Group] = {
    None
  }
}
