package com.tapi.lockboxview.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.tapi.lockboxview.R

fun Context.showDialog(onConfirmed: () -> Unit) {
    val dialog = TestDialog(context = this)
    dialog.show()
}

class TestDialog(
    private val context: Context
) : FlexibleDialog(context) {
    override fun createView(): View {
        val view =
            LayoutInflater.from(context).inflate(R.layout.battery_confirm_dialog, null, false)
        setContentView(view)
        return view
    }

    override fun initViews() {

    }

}