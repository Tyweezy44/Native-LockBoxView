package com.tapi.lockboxview

import android.os.Bundle
import android.view.animation.RotateAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.tapi.lockboxview.custom.LockBoxView

class MainActivity : AppCompatActivity() {

    private var mView: LockBoxView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()


    }

    private fun initViews() {
        mView = findViewById(R.id.lock_view)
        mView?.loadViews()
    }

}