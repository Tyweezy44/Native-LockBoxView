package com.tapi.lockboxview.battery

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.tapi.lockboxview.battery.drawers.CircleDrawer
import com.tapi.lockboxview.battery.drawers.DynamicViewDrawer
import com.tapi.lockboxview.battery.drawers.ProcessDrawer

class BatteryView : View {

    private val dynamicViewDrawer = DynamicViewDrawer(context, this::invalidate)
    private val processDrawer = ProcessDrawer(context, this::invalidate)
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
        dynamicViewDrawer.draw(canvas)

    }

    fun setPercent(fl: Float) {
        dynamicViewDrawer.setPercent(fl)
        processDrawer.setPercent(fl)
    }

    private fun setupDrawer(w: Int, h: Int) {
        circleDrawer.setup(w, h)
        processDrawer.setup(w, h)
        dynamicViewDrawer.setup(w, h)
    }


}
