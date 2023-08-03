package com.example.antivirus.applock.clean.ui.savebattery.battery_view.drawers

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class CircleDrawer : BaseDrawer() {

    private val circlePaint: Paint by lazy {
        val paint = Paint()
        paint.apply {
            color = Color.parseColor("#8060A3F3")
            style = Paint.Style.FILL
            isAntiAlias = true
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