package com.humbletrader.tlv

import com.humbletrader.tlv.data.{Group, Point, Rectangle, ScanConfig, ScannedDocument}
import com.humbletrader.tlv.detector.GroupDetector
import com.humbletrader.tlv.ops.DocumentOps

object MainApp extends DocumentOps{

  def main(args: Array[String]): Unit = {
    //todo: read this from a file or something
    implicit val config: ScanConfig = ScanConfig(1)

    //todo: read input rectangles from command line
    val inputRectangles = Seq(
      Rectangle(Point(100,100), Point(200,200)),
      Rectangle(Point(0, 0), Point(10, 100)),
      Rectangle(Point(10, 10), Point(50, 50))
    )

    var document = ScannedDocument(Seq.empty)
    inputRectangles.foreach{contour =>
      document = add(contour, document)
    }

    println(document)
  }

}
