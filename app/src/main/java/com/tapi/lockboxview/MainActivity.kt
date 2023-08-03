package com.tapi.lockboxview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.antivirus.applock.clean.ui.savebattery.battery_view.BatteryView
import com.tapi.lockboxview.text_scroll.TextAdapter


class MainActivity : AppCompatActivity() {


    var rvText: RecyclerView? = null
    var btView: BatteryView? = null
    val textAdapter: TextAdapter by lazy { TextAdapter() }

    private val snapHelper = LinearSnapHelper()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.text_layout_fragment)
//        rvText = findViewById(R.id.rv_text)
        btView = findViewById(R.id.battery_view)

        btView?.setPercent(40)
    }

    private fun setupRecyclerView() {
        rvText?.let {
            it.adapter = textAdapter
            it.layoutManager = LinearLayoutManager(this)

            it.setHasFixedSize(true)

            it.post {
                val padding =
                    (it.height / 2) - (resources.getDimension(R.dimen.text_height)/2).toInt()

                it.setPadding(0, padding, 0, padding)
            }
        }
        textAdapter.submitList(mockData(4))
        if (rvText != null) {
            snapHelper.attachToRecyclerView(rvText)
        }

    }

    private fun mockData(count: Int = 4): List<String> {
        val rs = mutableListOf<String>()
        repeat(count) {
            rs.add("item index $it")
        }
        return rs
    }

}