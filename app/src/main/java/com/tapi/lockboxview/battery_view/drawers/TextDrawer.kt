package com.example.antivirus.applock.clean.ui.savebattery.battery_view.drawers

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
    private var boundRectText: RectF = RectF()
    private  val rectNumber = Rect()
    private val rectPercentChar = Rect()

    private val textPaint: Paint by lazy {
        val paint = Paint()
        paint.apply {
            isAntiAlias = true
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

    override fun setPercent(percent: Int) {
        super.setPercent(percent)
        invalidate()
    }

    private fun drawPercentNumber(canvas: Canvas, percent: Int) {
        val content = percent.toString()

        textPaint.typeface = textTypeFace
        textPaint.textSize = percentNumberTextSize
        textPaint.getTextBounds(content, 0, content.length, rectNumber)
        canvas.drawText(
            content,
            (viewBound.width() - rectNumber.width()) / 2f,
            (viewBound.height() + rectNumber.height()) / 2f,
            textPaint
        )

        val left = (viewBound.width() - rectNumber.width()) / 2f
        val top = (viewBound.height() + rectNumber.height()) / 2f
        val right = left + rectNumber.width()
        val bottom = top + rectNumber.height()

        boundRectText.set(left, top, right, bottom)
    }

    private fun drawTextPercent(canvas: Canvas, text: String = "%") {
        textPaint.textSize = percentCharTextSize

        textPaint.getTextBounds(text, 0, text.length, rectPercentChar)
        canvas.drawText(
            text, boundRectText.right, boundRectText.top - rectPercentChar.height() * 2, textPaint
        )
    }

}