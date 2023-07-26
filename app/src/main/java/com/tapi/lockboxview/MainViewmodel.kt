package com.tapi.lockboxview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val percent = MutableLiveData<Float>()

    init {
        start()
    }

    fun start() {
        viewModelScope.launch {
            var count = 0f
            delay(200)
            while (count < 101) {
                delay(200)
                if (count == 100f) {
                    count = 0f
                }
                percent.value = count
                count++
            }
        }
    }
}