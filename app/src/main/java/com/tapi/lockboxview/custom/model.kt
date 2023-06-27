package com.tapi.lockboxview.custom



fun convertValue(min1: Float, max1: Float, min2: Float, max2: Float, value: Float): Float {
    return ((value - min1) * ((max2 - min2) / (max1 - min1)) + min2)
}


data class Axis(var x: Float = 0f, var y: Float = 0f)