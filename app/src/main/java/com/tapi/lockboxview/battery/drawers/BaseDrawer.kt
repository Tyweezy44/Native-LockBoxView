package com.tapi.lockboxview.battery.drawers

import android.graphics.Rect
import com.tapi.lockboxview.battery.absoluteDimension
import com.tapi.lockboxview.battery.model.Coordinate
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
const val PROGRESS_STROKE_WIDTH = 4f


open class BaseDrawer {
    protected var viewBound: Rect = Rect()

    protected val percentNumberTextSize: Float
        get() = viewBound.getDimensionWithRatio(
            RATIO_PERCENT_NUMBER
        )
    protected val percentCharTextSize: Float
        get() = viewBound.getDimensionWithRatio(
            RATIO_PERCENT_CHAR
        )
    protected val circleCenterWidth: Float get() = viewBound.getDimensionWithRatio(RATIO_CIRCLE)

    protected val lengthGap: Float get() = viewBound.getDimensionWithRatio(GAP_RATIO)

    protected val paddingBetween: Float get() = viewBound.getDimensionWithRatio(RATIO_PADDING)
    protected val progressStrokeWidth: Float
        get() = viewBound.getDimensionWithRatio(
            PROGRESS_STROKE_WIDTH
        )
    protected val backgroundStrokeWidth: Float
        get() = viewBound.getDimensionWithRatio(
            PROGRESS_STROKE_BACKGROUND_WIDTH
        )

    protected val radius: Float get() = circleCenterWidth / 2
    protected val radiusBound: Float get() = radius + paddingBetween


    protected val START_ANGLE = 135f
    protected val SWEEP_ANGLE = 270f
    protected val END_ANGLE: Float get() = Math.abs(360f - (START_ANGLE + SWEEP_ANGLE))

    private var percent: Float = 0f

    open fun setup(w: Int, h: Int) {
        this.viewBound.set(0, 0, w, h)
    }

    protected fun Rect.getDimensionWithRatio(ratioCalculate: Float): Float {
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
        xCenter: Float,
        yCenter: Float,
        radius: Float,
        degree: Float
    ): Coordinate {
        val radian = Math.toRadians(degree.toDouble())
        val x = xCenter + (radius) * cos(radian).toFloat()
        val y = yCenter + (radius) * sin(radian).toFloat()
        return Coordinate(x, y)
    }

    /**
     * Percent in range : 0..100
     */
    open fun setPercent(percent: Float) {
        this.percent = max(min(percent, 100f), 0f)
    }

    protected fun getPercent(): Float = percent

}