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

    private var score = MutableLiveData<Int>()

    fun setPhrases(list : List<Phrase>) {
        phrases = list
    }

    fun getQuizList() : ArrayList<Phrase>{
        return quizList
    }

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

        // if selected option is correct/wrong
        if(tv.text == quizList[mCurrentQuizPos].chTrans) {
            score.value = score.value?.plus(1)
            println("right, score: " + score)
        } else if(tv.text != quizList[mCurrentQuizPos].chTrans) {
            //Toast.makeText(activity,"false", Toast.LENGTH_SHORT).show()
            println("false")
        }
    }

    fun defaultOptionsView(options : ArrayList<TextView>) {
        for (o in options) {
            o.setTextColor(Color.parseColor("#FF000000"))
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