package com.tapi.lockboxview.battery

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.tapi.lockboxview.battery.drawers.CircleDrawer
import com.tapi.lockboxview.battery.drawers.ProcessDrawer
import com.tapi.lockboxview.battery.drawers.TextDrawer

class BatteryView : View {

    private val textDrawer = TextDrawer(context, this::invalidate)
    private val processDrawer = ProcessDrawer(this::invalidate)
    private val circleDrawer = CircleDrawer()

    constructor(context: Context) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    )


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setupDrawer(w, h)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return
        drawViews(canvas)

    }

    private fun drawViews(canvas: Canvas) {
        circleDrawer.draw(canvas)
        processDrawer.draw(canvas)
        textDrawer.draw(canvas)

    }

    fun setPercent(fl: Float) {
        textDrawer.setPercent(fl)
        processDrawer.setPercent(fl)
    }

    private fun setupDrawer(w: Int, h: Int) {
        circleDrawer.setup(w, h)
        processDrawer.setup(w, h)
        textDrawer.setup(w, h)
    }

}
