package com.example.norwegianphrases.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val _instruction = MutableLiveData<String>().apply {
        value = "每题给出一句挪威语短句，从三个中文选项中选择正确的选项。"
    }

    val instruction: LiveData<String> = _instruction

}