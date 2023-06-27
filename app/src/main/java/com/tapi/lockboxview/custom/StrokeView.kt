package com.tapi.lockboxview.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.toRect
import com.tapi.lockboxview.R
import com.tapi.lockboxview.custom.LockBoxView.Companion.KEY_FRAME_COMPLETE
import com.tapi.lockboxview.custom.LockBoxView.Companion.KEY_FRAME_COMPLETE_CLOSE

class StrokeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private lateinit var viewBound: RectF

    private var scaleStroke: Float = 1f

    private val mPaint = Paint().apply {
        style = Paint.Style.STROKE
        isAntiAlias = true
    }

    private val mPaintBg = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val shader: LinearGradient
        get() = LinearGradient(
            0f,
            0f,
            0f,
            viewBound.height() / 2f,
            0xFF00C3FB.toInt(),
            0xFF005EEB.toInt(),
            Shader.TileMode.CLAMP
        )

    val highlightView: Drawable by lazy {
        context.resources.getDrawable(R.drawable.blur_background, null)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewBound = RectF(0f, 0f, w * 1f, h * 1f)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas ?: return
        drawBackground(canvas)
        drawHighLightBackground(canvas)
    }

    private fun drawBackground(canvas: Canvas) {
        mPaintBg.shader = shader
        canvas.drawRect(viewBound, mPaintBg)
    }

    private fun drawHighLightBackground(canvas: Canvas) {
        highlightView.bounds = viewBound.toRect()
        highlightView.draw(canvas)

        mPaint.shader = shader
        mPaint.strokeWidth = viewBound.height() * scaleStroke

        canvas.drawCircle(viewBound.width() / 2, viewBound.height() / 2, viewBound.height(), mPaint)
    }


    fun handleStroke(value: Float) {
        scaleStroke = if (value >= KEY_FRAME_COMPLETE) {
            convertValue(KEY_FRAME_COMPLETE, 1f, 0f, 3f, value)
        } else {
            convertValue(KEY_FRAME_COMPLETE_CLOSE, 1f, 1f, 0f, value)
        }
        invalidate()
    }

}