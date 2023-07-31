package com.tapi.lockboxview.battery.drawers

import android.animation.ValueAnimator
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.view.animation.LinearInterpolator
import com.tapi.lockboxview.battery.drawLineGap
import com.tapi.lockboxview.battery.model.Coordinate
import com.tapi.lockboxview.battery.model.PairCoordinate
import com.tapi.lockboxview.custom.convertValue

class ProcessDrawer(
    private val invalidate: () -> Unit
) : BaseDrawer() {

    private var oldPercent: Float = 0f

    private val neonPaint: Paint by lazy {
        val paint = Paint()
        paint.apply {
            color = Color.parseColor("#FF03DAC5")
            style = Paint.Style.STROKE
            strokeWidth = backgroundStrokeWidth
            strokeCap = Paint.Cap.ROUND
            maskFilter = BlurMaskFilter(10f, BlurMaskFilter.Blur.OUTER)
        }
        paint
    }

    private val backgroundProcessPaint: Paint by lazy {
        val paint = Paint()
        paint.apply {
            color = Color.parseColor("#FFFFFF")
            style = Paint.Style.STROKE
            strokeWidth = backgroundStrokeWidth
            strokeCap = Paint.Cap.ROUND
        }
        paint
    }

    private val processPaint: Paint by lazy {
        val paint = Paint()
        paint.apply {
            color = Color.parseColor("#7DF6FF")
            style = Paint.Style.STROKE
            strokeWidth = progressStrokeWidth
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
        }
        paint
    }

    private var processAnimate: ValueAnimator? = null

    private val rectBoundArc: RectF get() = getViewBoundArc()
    val path = Path()

    fun draw(canvas: Canvas) {
        //draw background progress
        drawArcSwipe(canvas, SWEEP_ANGLE, neonPaint)
        drawArcSwipe(canvas, SWEEP_ANGLE, backgroundProcessPaint)
        drawGap(canvas)

        //draw progress
        val angle = convertValue(0f, 100f, 0f, SWEEP_ANGLE, oldPercent)
        drawArcSwipe(canvas, angle, processPaint)

    }

    override fun setPercent(percent: Float) {
        super.setPercent(percent)
        startAnimation()
    }

    private fun startAnimation() {
        if (processAnimate != null) {
            processAnimate?.cancel()
            processAnimate = null
            oldPercent = getPercent()
        }

        processAnimate = ValueAnimator.ofFloat(oldPercent, getPercent()).apply {
            duration = 500L
            interpolator = LinearInterpolator()

            addUpdateListener {
                val value = it.animatedValue as Float
                oldPercent = value
                invalidate()
            }
            start()
        }
    }

    private fun drawArcSwipe(canvas: Canvas, sweepAngle: Float, paint: Paint) {
        canvas.drawArc(
            rectBoundArc, START_ANGLE, sweepAngle, false, paint
        )
    }

    private fun drawGap(canvas: Canvas) {
        val leftAxis = getLeftAxis()
        val rightAxis = getRightAxis()


        canvas.drawLineGap(leftAxis.start, leftAxis.end, backgroundProcessPaint)
        canvas.drawLineGap(rightAxis.start, rightAxis.end, backgroundProcessPaint)

        val leftPaint = if (oldPercent > 0) processPaint else neonPaint
        val rightPaint = if (oldPercent < 100) neonPaint else processPaint

        canvas.drawLineGap(leftAxis.start, leftAxis.end, leftPaint)
        canvas.drawLineGap(rightAxis.start, rightAxis.end, rightPaint)
    }

    private fun getLeftAxis(): PairCoordinate {
        val start = getAxisGap(radiusBound, START_ANGLE)
        val end = getAxisGap(radiusBound - lengthGap, START_ANGLE)
        return PairCoordinate(start, end)
    }

    private fun getRightAxis(): PairCoordinate {
        val start = getAxisGap(radiusBound, END_ANGLE)
        val end = getAxisGap(radiusBound - lengthGap, END_ANGLE)
        return PairCoordinate(start, end)
    }

    private fun getViewBoundArc(): RectF {
        val padding = radius + paddingBetween
        return RectF(
            viewBound.centerX() - padding,
            viewBound.centerY() - padding,
            viewBound.centerX() + padding,
            viewBound.centerY() + padding
        )
    }

    private fun getAxisGap(
        radius: Float, degree: Float
    ): Coordinate {
        return getAxis(
            viewBound.centerX().toFloat(), viewBound.centerY().toFloat(), radius, degree
        )
    }

}