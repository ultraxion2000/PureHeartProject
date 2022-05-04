package com.example.pureheart.ui.Home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Большое сердце, как и океан, никогда не замерзает"

    }
    val text: LiveData<String> = _text

}