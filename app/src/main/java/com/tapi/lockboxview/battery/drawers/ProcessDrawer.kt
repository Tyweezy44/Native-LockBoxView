package com.tapi.lockboxview.battery.drawers

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.view.animation.LinearInterpolator
import com.tapi.lockboxview.battery.dpToPx
import com.tapi.lockboxview.custom.convertValue

class ProcessDrawer(
    private val context: Context, private val invalidate: () -> Unit
) : BaseDrawer() {

    private var oldPercent: Float = 0f

    private val backgroundProcessPaint: Paint by lazy {
        val paint = Paint()
        paint.apply {
            color = Color.parseColor("#FFFFFF")
            style = Paint.Style.STROKE
            strokeWidth = context.dpToPx(2f)
            strokeCap = Paint.Cap.ROUND
        }
        paint
    }

    private val processPaint: Paint by lazy {
        val paint = Paint()
        paint.apply {
            color = Color.parseColor("#7DF6FF")
            style = Paint.Style.STROKE
            strokeWidth = context.dpToPx(2f)
        }
        paint
    }


    private var processAnimate: ValueAnimator? = null

    private val rectBoundArc: RectF get() = getViewBoundArc()
    val path = Path()

    fun draw(canvas: Canvas) {
        drawArcSwipe(canvas, SWEEP_ANGLE, backgroundProcessPaint)

        val angle = convertValue(0f, 100f, 0f, SWEEP_ANGLE, oldPercent)

        drawArcSwipe(canvas, angle, processPaint)

        drawGap(canvas)
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
            duration = 350
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
        val percent = getPercent()
        val radiusBound = radius + paddingBetween

        val (xLeftStart, yLeftStart) = getAxisGap(
            radiusBound, START_ANGLE
        )

        val (xLeftEnd, yLeftEnd) = getAxisGap(
            radiusBound - lengthGap, START_ANGLE
        )

        val (xRightStart, yRightStart) = getAxisGap(
            radiusBound, END_ANGLE
        )

        val (xRightEnd, yRightEnd) = getAxisGap(radiusBound - lengthGap, END_ANGLE)

        canvas.drawLine(
            xLeftStart,
            yLeftStart,
            xLeftEnd,
            yLeftEnd,
            if (percent > 0) processPaint else backgroundProcessPaint
        )
        canvas.drawLine(
            xRightStart,
            yRightStart,
            xRightEnd,
            yRightEnd,
            if (percent < 100) backgroundProcessPaint else processPaint
        )

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
    ): Pair<Float, Float> {
        return getAxis(
            viewBound.centerX().toFloat(), viewBound.centerY().toFloat(), radius, degree
        )
    }

}