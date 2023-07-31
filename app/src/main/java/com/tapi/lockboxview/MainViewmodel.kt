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
            delay(1000)
            percent.value = 50f
        }
    }
}