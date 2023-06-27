package com.tapi.lockboxview.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import com.tapi.lockboxview.custom.LockBoxView.Companion.KEY_FRAME_COMPLETE_CLOSE
import com.tapi.lockboxview.custom.LockBoxView.Companion.KEY_FRAME_OPEN
import com.tapi.lockboxview.custom.LockBoxView.Companion.KEY_FRAME_START_CLOSE
import com.tapi.lockboxview.R

class DoorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    val lockDoor: Drawable by lazy {
        context.resources.getDrawable(R.drawable.lock_door_icon, null)
    }

    private lateinit var viewBound: Rect

    var scaleW = 1f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewBound = Rect(0, 0, w, h)
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return

        drawDoor(canvas)
    }

    private fun drawDoor(canvas: Canvas) {
        lockDoor.setBounds(
            viewBound.left, viewBound.top, (viewBound.width() * scaleW).toInt(), viewBound.height()
        )

        lockDoor.draw(canvas)
    }

    fun handleValue(value: Float) {
        if (value < KEY_FRAME_OPEN) {
            scaleW = convertValue(
                0f, KEY_FRAME_OPEN, 1f, 0.75f, value
            )
        } else if (value > KEY_FRAME_START_CLOSE && value < KEY_FRAME_COMPLETE_CLOSE) {
            scaleW = convertValue(
                KEY_FRAME_START_CLOSE, KEY_FRAME_COMPLETE_CLOSE, 0.75f, 1f, value
            )

        }
        invalidate()
    }
}