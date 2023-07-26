package com.tapi.lockboxview.battery

import android.content.Context
import android.graphics.Rect
import kotlin.math.min

fun Rect.absoluteDimension(): Int {
    return min(width(), height())
}

fun Context.dpToPx(dp: Float): Float {
    return dp * resources.displayMetrics.density + 0.5f
}
