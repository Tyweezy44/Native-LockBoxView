package com.tapi.lockboxview.custom

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.animation.doOnEnd

class IconView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {


    private var mAxis: Axis = Axis()
    private var animate: ValueAnimator? = null
    private lateinit var viewBound: Rect

    val isAnimating: Boolean get() = animate != null

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewBound = Rect(0, 0, w, h)

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return
    }

    fun startAnimation(
        axis: Axis, timeDelay: Long, isRotate: Boolean = true, doOnEnd: () -> Unit = {}
    ) {
        if (animate != null) {
            animate?.cancel()
            animate = null
        }
        animate = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = timeDelay
            repeatMode = ValueAnimator.RESTART

            addUpdateListener {
                val value = it.animatedValue as Float
                if (mAxis.x != 0f || mAxis.y != 0f) {
                    val x = convertValue(0f, 1f, mAxis.x, axis.x, value)
                    val y = convertValue(0f, 1f, mAxis.y, axis.y, value)

                    this@IconView.x = x - width / 2f
                    this@IconView.y = y - height / 2f
                    if (isRotate) {
                        this@IconView.rotation = convertValue(
                            0f, 1f, this@IconView.rotation, this@IconView.rotation + 3, value
                        )
                    }
                    requestFocus()
                }
            }
            interpolator = AccelerateDecelerateInterpolator()
            start()
            doOnEnd {
                mAxis = axis
                doOnEnd()
                animate = null
            }
        }
    }

    fun stopAnimate() {
        animate?.cancel()
        animate = null
    }

    fun initStartPoint(axis: Axis) {
        this.mAxis = axis
    }

    companion object {
        const val FRAME_START = 4
        const val FRAME_SECOND = 5
        const val FRAME_THIRD = 6
        const val FRAME_END = 7
    }

}