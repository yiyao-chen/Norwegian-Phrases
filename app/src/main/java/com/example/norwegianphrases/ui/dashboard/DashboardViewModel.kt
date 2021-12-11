package com.example.norwegianphrases.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val _instruction = MutableLiveData<String>().apply {
        value = "zuo ti"
    }

    val instruction: LiveData<String> = _instruction

}