package com.example.antivirus.applock.clean.ui.savebattery.battery_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.example.antivirus.applock.clean.ui.savebattery.battery_view.drawers.CircleDrawer
import com.example.antivirus.applock.clean.ui.savebattery.battery_view.drawers.ProcessDrawer
import com.example.antivirus.applock.clean.ui.savebattery.battery_view.drawers.TextDrawer
import com.example.antivirus.applock.clean.ui.savebattery.battery_view.model.Coordinate
import kotlin.math.min

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

    fun setPercent(level: Int) {
        textDrawer.setPercent(level)
        processDrawer.setPercent(level)
    }

    private fun setupDrawer(w: Int, h: Int) {
        circleDrawer.setup(w, h)
        processDrawer.setup(w, h)
        textDrawer.setup(w, h)
    }

}

fun Rect.absoluteDimension(): Int {
    return min(width(), height())
}

fun Canvas.drawLineGap(start: Coordinate, end: Coordinate, paint: Paint) {
    drawLine(start.x, start.y, end.x, end.y, paint)
}
