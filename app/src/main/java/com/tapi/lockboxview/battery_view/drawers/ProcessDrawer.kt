package com.example.antivirus.applock.clean.ui.savebattery.battery_view.drawers

import android.animation.ValueAnimator
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.view.animation.LinearInterpolator
import com.tapi.lockboxview.battery_view.drawLineGap
import com.example.antivirus.applock.clean.ui.savebattery.battery_view.model.Coordinate
import com.example.antivirus.applock.clean.ui.savebattery.battery_view.model.PairCoordinate
import com.tapi.lockboxview.custom.convertValue


class ProcessDrawer(
    private val invalidate: () -> Unit
) : BaseDrawer() {

    private var oldPercent: Int = 0

    private val neonPaint: Paint by lazy {
        val paint = Paint()
        paint.apply {
            color = Color.parseColor("#FF03DAC5")
            style = Paint.Style.STROKE
            strokeWidth = backgroundStrokeWidth
            strokeCap = Paint.Cap.ROUND
        }
        paint
    }

    private val backgroundProcessPaint: Paint by lazy {
        val paint = Paint()
        paint.apply {
            isAntiAlias = true
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
            isAntiAlias = true
            color = Color.parseColor("#7DF6FF")
            style = Paint.Style.STROKE
            strokeWidth = progressStrokeWidth
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
        }
        paint
    }

    private var processAnimate: ValueAnimator? = null

    private var rectBoundArc: RectF = RectF()
    val path = Path()

    private lateinit var leftAxis: PairCoordinate
    private lateinit var rightAxis: PairCoordinate

    private lateinit var originalBitmap: Bitmap
    private lateinit var blurredBitmap: Bitmap
    private val blurAlpha = 40

    override fun setup(w: Int, h: Int) {
        super.setup(w, h)
        rectBoundArc.set(getViewBoundArc())
        leftAxis = getLeftAxis()
        rightAxis = getRightAxis()

        originalBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        blurredBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
    }

    fun draw(canvas: Canvas) {
        //draw background progress

        drawNeonBackground(canvas)
        drawGapBackground(canvas)

        drawGapProgress(canvas)
        drawArcSwipe(canvas, sweepAngle, backgroundProcessPaint)

        //draw progress
        drawProgress(canvas)
    }


    override fun setPercent(percent: Int) {
        super.setPercent(percent)
        startAnimation()
    }

    private fun drawNeonBackground(canvas: Canvas) {
        for (i in 0..10) {
            val thickness = 10f + i * 2f
            neonPaint.strokeWidth = thickness
            neonPaint.alpha = blurAlpha - i * 4
            drawArcSwipe(canvas, sweepAngle, neonPaint)
            canvas.drawLineGap(leftAxis.start, leftAxis.end, neonPaint)
            canvas.drawLineGap(rightAxis.start, rightAxis.end, neonPaint)
        }
    }

    private fun drawGapBackground(canvas: Canvas) {
        canvas.drawLineGap(leftAxis.start, leftAxis.end, backgroundProcessPaint)
        canvas.drawLineGap(rightAxis.start, rightAxis.end, backgroundProcessPaint)
    }

    private fun drawGapProgress(canvas: Canvas) {
        if (oldPercent > 0) {
            canvas.drawLineGap(leftAxis.start, leftAxis.end, processPaint)
        }
        if (oldPercent == 100) {
            canvas.drawLineGap(rightAxis.start, rightAxis.end, processPaint)
        }
    }

    private fun drawArcSwipe(canvas: Canvas, sweepAngle: Float, paint: Paint) {
        canvas.drawArc(
            rectBoundArc, startAngle, sweepAngle, false, paint
        )
    }

    private fun drawProgress(canvas: Canvas) {
        val angle = convertValue(0f, 100f, 0f, sweepAngle, oldPercent.toFloat())
        drawArcSwipe(canvas, angle, processPaint)
    }

    private fun startAnimation() {
        if (processAnimate != null) {
            processAnimate?.cancel()
            processAnimate = null
            oldPercent = getPercent()
        }

        processAnimate = ValueAnimator.ofFloat(oldPercent.toFloat(), getPercent().toFloat()).apply {
            duration = 1000L
            interpolator = LinearInterpolator()

            addUpdateListener {
                val value = it.animatedValue as Float
                oldPercent = value.toInt()
                invalidate()
            }
            start()
        }
    }

    private fun getLeftAxis(): PairCoordinate {
        val start = getAxisGap(radiusBound, startAngle)
        val end = getAxisGap(radiusBound - lengthGap, startAngle)
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