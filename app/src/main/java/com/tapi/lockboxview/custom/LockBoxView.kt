package com.tapi.lockboxview.custom

import android.animation.ValueAnimator
import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import com.tapi.lockboxview.R
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class LockBoxView : FrameLayout {

    private var iconView: IconView? = null
    private var iconView2: IconView? = null
    private var iconView3: IconView? = null
    private var iconView4: IconView? = null
    private var iconView5: IconView? = null
    private var starView: IconView? = null

    private var boxIcon: AppCompatImageView? = null
    private var doorIcon: DoorView? = null
    private var strokeView: StrokeView? = null

    private lateinit var iconViews: List<IconView?>

    var count = 0

    private val handler = Handler()
    private var animate: ValueAnimator? = null
    private var degree: Float = 0f

    val timeDelay: Float by lazy {
        (KEY_FRAME_START_CLOSE - KEY_FRAME_OPEN) * ALL_TIME_RUN / (IconView.FRAME_END + 2)
    }

    private val runnable: Runnable = Runnable {
        drawFileViews()
    }

    private val centerPoint: Axis
        get() {
            return if (boxIcon != null) {
                val centerX = boxIcon!!.x + boxIcon!!.width / 2f
                val centerY = boxIcon!!.y + boxIcon!!.height / 2f
                Axis(centerX, centerY)
            } else {
                Axis()
            }
        }

    var isRunning = false

    constructor(
        context: Context,
    ) : super(context) {
        initViews()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initViews()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        initViews()
    }

    private fun initViews() {
        val layout = inflate(context, R.layout.box_layout, this)

        iconView = layout.findViewById(R.id.icon_view_1)
        iconView2 = layout.findViewById(R.id.icon_view_2)
        iconView3 = layout.findViewById(R.id.icon_view_3)
        iconView4 = layout.findViewById(R.id.icon_view_4)
        iconView5 = layout.findViewById(R.id.icon_view_5)
        starView = layout.findViewById(R.id.star_view)
        iconViews = listOf(iconView, iconView2, iconView3, iconView4, iconView5)

        doorIcon = layout.findViewById(R.id.door_icon)
        boxIcon = layout.findViewById(R.id.box_icon)
        strokeView = layout.findViewById(R.id.stroke_view)

        initFileIconAxis()
    }

    private fun initFileIconAxis() {
        boxIcon?.let {
            for (i in iconViews.indices) {
                iconViews[i]?.initStartPoint(Axis(centerPoint.x, centerPoint.y))
            }
        }

    }

    fun loadViews() {
        startAnimation()
    }

    private fun startAnimation() {
        if (animate != null) return
        animate = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = ALL_TIME_RUN
            repeatMode = ValueAnimator.RESTART
            repeatCount = ValueAnimator.INFINITE

            addUpdateListener {
                val value = it.animatedValue as Float
                handleRotateBox(value)

                if (value in KEY_FRAME_OPEN..KEY_FRAME_START_CLOSE) {
                    this@LockBoxView.start()
                } else if (value > KEY_FRAME_START_CLOSE) {
                    this@LockBoxView.removes()
                }

                handleStarIcon(value)
                strokeView?.handleStroke(value)
            }
            start()

        }
    }

    private fun handleStarIcon(value: Float) {
        if (value in KEY_FRAME_START_CLOSE..KEY_FRAME_COMPLETE_CLOSE) {
            val alpha = convertValue(KEY_FRAME_START_CLOSE, KEY_FRAME_COMPLETE_CLOSE, 0f, 1f, value)
            starView?.alpha = alpha
        } else if (value >= KEY_FRAME_COMPLETE) {
            val alpha = convertValue(KEY_FRAME_COMPLETE, 1f, 1f, 0f, value)
            starView?.alpha = alpha
        }
    }

    private fun start() {
        if (!isRunning) {
            isRunning = true
            handler.post(runnable)
        }
    }

    private fun removes() {
        if (isRunning) {
            isRunning = false
            handler.removeCallbacks(runnable)
        }
    }

    private fun drawFileViews() {
        boxIcon?.let { box ->
            val radius = if (count == 0) 0f else box.width * 0.8f


            val iconViews = listOf(iconView, iconView2, iconView3, iconView4, iconView5)

            for (i in iconViews.indices) {
                val condition = when (i) {
                    0 -> IconView.FRAME_END
                    1 -> IconView.FRAME_THIRD
                    2 -> IconView.FRAME_SECOND
                    else -> IconView.FRAME_START
                }
                val axis =
                    increase(centerPoint.x, centerPoint.y, if (count >= condition) 0f else radius, count + i)
                iconViews[i]?.startAnimation(axis, timeDelay.toLong())
            }

            increaseCount()
            handler.postDelayed(runnable, timeDelay.toLong())
        }
    }

    private fun increaseCount() {
        if (count == IconView.FRAME_END) {
            count = 0
        } else {
            count++
        }
    }

    private fun handleRotateBox(value: Float) {
        if (value < KEY_FRAME_OPEN) {
            degree = convertValue(
                0f, KEY_FRAME_OPEN, 0f, 10f, value
            )
        } else if (value > KEY_FRAME_START_CLOSE && value < KEY_FRAME_COMPLETE_CLOSE) {
            degree = convertValue(
                KEY_FRAME_START_CLOSE, KEY_FRAME_COMPLETE_CLOSE, 10f, 0f, value
            )
        }

        boxIcon?.rotation = -degree
        doorIcon?.rotation = -degree
        doorIcon?.handleValue(value)

    }

    private fun increase(xCenter: Float, yCenter: Float, radius: Float, phase: Int): Axis {
        val degree = ((phase - 1) * Random.nextInt(from = 55, until = 60)) % 360
        val radian = Math.toRadians(degree.toDouble())
        val x = xCenter + radius * cos(radian).toFloat()
        val y = yCenter + radius * sin(radian).toFloat()
        return Axis(x, y)
    }

    companion object {
        const val KEY_FRAME_OPEN = 0.02f
        const val KEY_FRAME_START_CLOSE = 0.5f
        const val KEY_FRAME_COMPLETE_CLOSE = 0.7f
        const val KEY_FRAME_COMPLETE = 0.9f
        const val ALL_TIME_RUN = 8000L

    }
}