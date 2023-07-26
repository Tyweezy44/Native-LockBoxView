package com.tapi.lockboxview.battery.drawers

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class CircleDrawer : BaseDrawer() {

    private val circlePaint: Paint by lazy {
        val paint = Paint()
        paint.apply {
            color = Color.parseColor("#60A3F3")
            style = Paint.Style.FILL
        }
        paint
    }

    fun draw(canvas: Canvas) {
        canvas.drawCircle(
            viewBound.centerX().toFloat(),
            viewBound.centerY().toFloat(),
            circleCenterWidth / 2,
            circlePaint
        )
    }
}