package com.example.antivirus.applock.clean.ui.savebattery.battery_view.drawers

import android.graphics.Rect
import com.example.antivirus.applock.clean.ui.savebattery.battery_view.absoluteDimension
import com.example.antivirus.applock.clean.ui.savebattery.battery_view.model.Coordinate
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin

const val RATIO_CIRCLE = 202f
const val RATIO_PERCENT_NUMBER = 64f
const val RATIO_PERCENT_CHAR = 34f
const val RATIO_WIDTH = 300f
const val RATIO_HEIGHT = 275f
const val RATIO_PADDING = 20f
const val GAP_RATIO = 10f
const val PROGRESS_STROKE_BACKGROUND_WIDTH = 2f
const val PROGRESS_STROKE_WIDTH = 5f


open class BaseDrawer {
    protected var viewBound: Rect = Rect()
    protected var percentNumberTextSize: Float = 0f

    protected var percentCharTextSize: Float = 0f
    protected var circleCenterWidth: Float = 0f

    protected var lengthGap: Float = 0f
    protected var paddingBetween: Float = 0f
    protected var progressStrokeWidth: Float = 0f
    protected var backgroundStrokeWidth: Float = 0f

    protected var radius: Float =0f
    protected var radiusBound: Float=0f

    protected val startAngle = 135f
    protected val sweepAngle = 270.2f
    protected val END_ANGLE: Float get() = Math.abs(360f - (startAngle + sweepAngle))

    private var percent: Int = 0

    open fun setup(w: Int, h: Int) {
        this.viewBound.set(0, 0, w, h)
        backgroundStrokeWidth = viewBound.getDimensionWithRatio(
            PROGRESS_STROKE_BACKGROUND_WIDTH
        )
        percentNumberTextSize = viewBound.getDimensionWithRatio(
            RATIO_PERCENT_NUMBER
        )
        percentCharTextSize = viewBound.getDimensionWithRatio(
            RATIO_PERCENT_CHAR
        )
        circleCenterWidth = viewBound.getDimensionWithRatio(RATIO_CIRCLE)
        lengthGap = viewBound.getDimensionWithRatio(GAP_RATIO)
        paddingBetween = viewBound.getDimensionWithRatio(RATIO_PADDING)
        progressStrokeWidth = viewBound.getDimensionWithRatio(
            PROGRESS_STROKE_WIDTH
        )
        radius = circleCenterWidth / 2
        radiusBound = radius + paddingBetween
    }

    private fun Rect.getDimensionWithRatio(ratioCalculate: Float): Float {
        val ratio = if (width() <= height()) {
            ratioCalculate / RATIO_WIDTH
        } else {
            val realRatio = width().toFloat() / height().toFloat()
            if (realRatio < 0.5f) {
                ratioCalculate / RATIO_HEIGHT
            } else {
                ratioCalculate / RATIO_WIDTH
            }
        }
        return absoluteDimension() * ratio
    }

    protected fun getAxis(
        xCenter: Float, yCenter: Float, radius: Float, degree: Float
    ): Coordinate {
        val radian = Math.toRadians(degree.toDouble())
        val x = xCenter + (radius) * cos(radian).toFloat()
        val y = yCenter + (radius) * sin(radian).toFloat()
        return Coordinate(x, y)
    }

    /**
     * Percent in range : 0..100
     */
    open fun setPercent(percent: Int) {
        this.percent = max(min(percent, 100), 0)
    }

    protected fun getPercent(): Int = percent

}