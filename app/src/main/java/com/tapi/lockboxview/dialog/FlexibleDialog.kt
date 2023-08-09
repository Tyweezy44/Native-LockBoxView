package com.tapi.lockboxview.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.tapi.lockboxview.R

abstract class FlexibleDialog(context: Context) : Dialog(context, R.style.BatteryDialogTheme),
    View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createView()
        configWindow()
        initViews()
    }

    open fun configWindow() {
        val window = window ?: return
        val widthPx = context.resources.displayMetrics.widthPixels
        val resultWidth = context.dpToPx(328f)

        Log.d("ManhNQ", "configWindow: $resultWidth -- $widthPx")

        val width = if (widthPx < resultWidth) {
            val padding = context.dpToPx(16f)
            widthPx - padding * 2
        } else {
            resultWidth
        }

        window.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        val result = shiftArray(9, 23)
        Log.d("ManhNQ", "configWindow: ${result.toString()}")

    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

    abstract fun createView(): View
    abstract fun initViews()

    fun shiftArray(inputNum: Int, count: Int): List<Int> {

        if (inputNum !in 0..count) {
            throw IllegalArgumentException("Input number should be an integer between 1 and 5.")
        }

        val arr = Array(count) { i -> (i + inputNum) % count }
        return arr.toList()
    }
}

fun Context.dpToPx(dp: Float): Int {
    return (dp * resources.displayMetrics.density + 0.5f).toInt()
}