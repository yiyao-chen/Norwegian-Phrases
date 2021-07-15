package com.example.norwegianphrases.ui.quiz

import android.graphics.Color
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.norwegianphrases.database.Phrase

class QuizViewModel : ViewModel() {

    private var phrases = listOf<Phrase>() // all phrases
    private var quizList = arrayListOf<Phrase>() // 5 quizes

    var mCurrentQuizPos: Int = 0 // counter
    var mSelectedOptionPos : Int = 0

    private val _quiz = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val quiz: LiveData<String> = _quiz

    fun setPhrases(list : List<Phrase>) {
        phrases = list
    }

    fun getQuizList() : ArrayList<Phrase>{
        return quizList
    }

    fun selectQuizes() {
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

    fun shuffleOptions(currentQuiz: Phrase): List<Phrase> {
        val phraseList: ArrayList<Phrase> = arrayListOf() // collection of options
        phraseList.addAll(phrases)

        val optionsList: ArrayList<Phrase> = arrayListOf()
        optionsList.add(currentQuiz) // add currect option to list

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


    fun changeSelectedOptionView(tv: TextView, selectedOptionNum: Int) {
        mSelectedOptionPos = selectedOptionNum
        tv.setTextColor(Color.parseColor("#FFBB86FC"))
        if(tv.text == quizList[mCurrentQuizPos].chTrans) {
            //Toast.makeText(activity,"RRR", Toast.LENGTH_SHORT).show()
            println("right")
        } else if(tv.text != quizList[mCurrentQuizPos].chTrans) {
            //Toast.makeText(activity,"false", Toast.LENGTH_SHORT).show()
            println("false")
        }
    }

    fun getCurrentQuizPos() : Int{
        return mCurrentQuizPos
    }

    fun increaseCurrentQuizPos(){
        mCurrentQuizPos++
    }


}