package com.tapi.lockboxview.battery

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import com.tapi.lockboxview.battery.model.Coordinate
import kotlin.math.min

fun Rect.absoluteDimension(): Int {
    return min(width(), height())
}

fun Canvas.drawLineGap(start: Coordinate, end: Coordinate, paint: Paint) {
    drawLine(start.x, start.y, end.x, end.y, paint)
}
