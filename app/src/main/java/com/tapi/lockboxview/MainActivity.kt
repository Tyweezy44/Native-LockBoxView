package com.tapi.lockboxview

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.tapi.lockboxview.battery.BatteryView
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


class MainActivity : AppCompatActivity() {
    val viewModel: MainViewModel by viewModels()

    var batteryView: BatteryView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()

        batteryView = findViewById(R.id.battery_view)
        viewModel.percent.observe(this) {
            batteryView?.setPercent(it)
        }
    }


    fun execRoot(command: String): String {
        return try {
            val process = Runtime.getRuntime().exec(command)
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            val output = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                output.append(line).append("\n")
            }
            output.toString()
        } catch (e: IOException) {
            e.printStackTrace()
            ""
        }
    }

    private fun initViews() {

    }

}