package com.tapi.lockboxview.duplicate

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import com.tapi.lockboxview.R
import com.tapi.lockboxview.custom.convertValue
import java.util.Random

class DuplicateView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private lateinit var viewBound: Rect

    var animator: ValueAnimator? = null

    val starIcon1: Drawable by lazy {
        context.resources.getDrawable(R.drawable.star_icon, null)
    }

    val starIcon2: Drawable by lazy {
        context.resources.getDrawable(R.drawable.star_icon, null)
    }

    val starIcon3: Drawable by lazy {
        context.resources.getDrawable(R.drawable.star_icon, null)
    }

    var alpha1 = 0f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewBound = Rect(0, 0, w, h)
    }

    fun start(){
        animator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 6000
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE


        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        starIcon1.setBounds(
           0, 0, 150, 150
        )

        starIcon2.setBounds(
            250, 250, 400, 400
        )
        starIcon3.setBounds(
            400, 400, 550, 550
        )
        starIcon1.alpha = alpha1.toInt().coerceAtMost(255)

        starIcon1.draw(canvas)


    }


}
