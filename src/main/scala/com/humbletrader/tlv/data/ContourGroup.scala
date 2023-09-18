package com.humbletrader.tlv.data

/**
 * A group of contours
 * @param name        just for debug / print - it does not have a real value
 * @param boundaries  the boundaries of the group
 */
case class ContourGroup(name: String, boundaries: Contour)
//todo: if needed the composing rectangles/contours may be kept here
// but for the moment we only keep the big enclosing aread