package com.example.norwegianphrases.ui.quiz

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.norwegianphrases.R
import com.example.norwegianphrases.database.Phrase

class QuizViewModel(application: Application) : AndroidViewModel(application) {

    private var phrases = listOf<Phrase>() // all phrases
    private var quizList = arrayListOf<Phrase>() // 5 quizes

    var mCurrentQuizPos: Int = 0 // counter for quiz-position in list
    var mSelectedOptionPos : Int = 0 // which option user selected

    lateinit var question: Phrase //current quiz

    private var score = MutableLiveData<Int>()

    fun setPhrases(list : List<Phrase>) {
        phrases = list
        question = phrases[mCurrentQuizPos]
    }

    fun getQuizList() : ArrayList<Phrase>{
        return quizList
    }

    // randomly select 3 phrases from phrasesList and add to global quizList
    fun selectQuizes() {
        score.value = 0

        val phraseList : ArrayList<Phrase> = arrayListOf() // collection of options
        phraseList.addAll(phrases)

        for(i in 1..3) {
            val randomIndex = (phraseList.indices).random()
            // add randomly selected quiz to quizList, and remove from collection to avoid duplicates
            val p = phraseList[randomIndex]
            quizList.add(p)
            phraseList.remove(p)
        }


    }

    // add correct answer to optionList, ramdomly select another 2 options and shuffle optionlist
    fun shuffleOptions(currentQuiz: Phrase): List<Phrase> {
        val phraseList: ArrayList<Phrase> = arrayListOf() // collection of options
        phraseList.addAll(phrases)

        val optionsList: ArrayList<Phrase> = arrayListOf()
        optionsList.add(currentQuiz) // add correct option to list

        phraseList.remove(currentQuiz) // remove from options to avoid duplicates

        // add two randomly selected options to list
        for (i in 1..2) {
            val randomIndex = (phraseList.indices).random()
            val p = phraseList[randomIndex]
            optionsList.add(p)
            phraseList.remove(p)
        }

        return optionsList.shuffled()
    }


    // change option view according to right/wrong
    fun changeSelectedOptionView(optionTv: TextView, selectedOptionNum: Int) {
        mSelectedOptionPos = selectedOptionNum
        optionTv.typeface = Typeface.DEFAULT
        // if selected option is correct/wrong
        if(optionTv.text == quizList[mCurrentQuizPos].chTrans) {
            score.value = score.value?.plus(1)
            optionTv.background = ContextCompat.getDrawable(
                getApplication(),
                R.drawable.correct_option_border_bg)
            println("right, score: " + score)
        } else if(optionTv.text != quizList[mCurrentQuizPos].chTrans) {
            //Toast.makeText(activity,"false", Toast.LENGTH_SHORT).show()
            println("false")
            optionTv.background = ContextCompat.getDrawable(
                getApplication(),
                R.drawable.wrong_option_border_bg)
        }
    }

    // set options text color back to default
    fun defaultOptionsView(options : ArrayList<TextView>) {
        for (o in options) {
            o.setTextColor(Color.parseColor("#808080"))
            o.typeface  = Typeface.DEFAULT
            o.background = ContextCompat.getDrawable(
                getApplication(),
                R.drawable.default_option_border_bg)
            o.isClickable = true
            o.isEnabled = true
        }

    }



    fun getCurrentQuizPos() : Int{
        return mCurrentQuizPos
    }

    fun getScore() : MutableLiveData<Int>{
        return score
    }

    fun increaseCurrentQuizPos(){
        mCurrentQuizPos++
    }


}