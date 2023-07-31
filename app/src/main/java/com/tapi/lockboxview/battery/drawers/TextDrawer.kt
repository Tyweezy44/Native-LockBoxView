package com.tapi.lockboxview.battery.drawers

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import com.tapi.lockboxview.R

class TextDrawer(private val context: Context, private val invalidate: () -> Unit) :
    BaseDrawer() {
    private var textRect: RectF = RectF()

    private val textPaint: Paint by lazy {
        val paint = Paint()
        paint.apply {
            color = Color.parseColor("#FFFFFF")
            style = Paint.Style.FILL
            typeface = textTypeFace
        }
        paint
    }

    private val textTypeFace: Typeface? by lazy {
        ResourcesCompat.getFont(context, R.font.bold)
    }

    fun draw(canvas: Canvas) {
        drawPercentNumber(canvas, getPercent())
        drawTextPercent(canvas)
    }

    override fun setPercent(percent: Float) {
        super.setPercent(percent)
        invalidate()
    }

    private fun drawPercentNumber(canvas: Canvas, percent: Float) {
        val content = "${percent.toInt()}"
        val rect = Rect()
        textPaint.typeface = textTypeFace
        textPaint.textSize = percentNumberTextSize
        textPaint.getTextBounds(content, 0, content.length, rect)
        canvas.drawText(
            content,
            (viewBound.width() - rect.width()) / 2f,
            (viewBound.height() + rect.height()) / 2f,
            textPaint
        )

        val left = (viewBound.width() - rect.width()) / 2f
        val top = (viewBound.height() + rect.height()) / 2f
        val right = left + rect.width()
        val bottom = top + rect.height()

        textRect.set(left, top, right, bottom)

    }

    private fun drawTextPercent(canvas: Canvas, text: String = "%") {
        val rect = Rect()
        textPaint.textSize = percentCharTextSize

        textPaint.getTextBounds(text, 0, text.length, rect)
        canvas.drawText(
            text, textRect.right, textRect.top - rect.height() * 2, textPaint
        )
    }

}