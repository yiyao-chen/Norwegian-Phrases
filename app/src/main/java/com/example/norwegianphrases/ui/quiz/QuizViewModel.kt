package com.example.norwegianphrases.ui.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {

    private val _quiz = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val quiz: LiveData<String> = _quiz
}