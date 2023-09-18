package com.humbletrader.tlv

import com.humbletrader.tlv.data.{Point, Contour, ToleranceConfig, ScannedDocument}
import com.humbletrader.tlv.ops.DocumentOps

object MainApp extends DocumentOps{

  def main(args: Array[String]): Unit = {
    //todo: read this from a file or cmd line
    implicit val config: ToleranceConfig = ToleranceConfig(1)

    //todo: read input rectangles from command line
    val inputRectangles = Seq(
      Contour(100, 100, 200, 200),
      Contour(0, 0, 10, 100),
      Contour(10, 10, 100, 150)
      //add here
    )

    var document = ScannedDocument.empty()
    inputRectangles.foreach{contour =>
      document = addContourToDocument(contour, document)
    }

    println(document)
  }

}
