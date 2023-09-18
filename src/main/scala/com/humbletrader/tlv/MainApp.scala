package com.humbletrader.tlv

import com.humbletrader.tlv.data.{Point, Rectangle, ToleranceConfig, ScannedDocument}
import com.humbletrader.tlv.ops.DocumentOps

object MainApp extends DocumentOps{

  def main(args: Array[String]): Unit = {
    //todo: read this from a file or something
    implicit val config: ToleranceConfig = ToleranceConfig(1)

    //todo: read input rectangles from command line
    val inputRectangles = Seq(
      Rectangle(100, 100, 200, 200),
      Rectangle(0, 0, 10, 100),
      Rectangle(10, 10, 50, 50)
      //add here
    )

    var document = ScannedDocument.empty()
    inputRectangles.foreach{contour =>
      document = addContour(contour, document)
    }

    println(document)
  }

}
