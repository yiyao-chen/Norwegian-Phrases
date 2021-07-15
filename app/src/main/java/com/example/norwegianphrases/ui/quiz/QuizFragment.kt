package com.example.norwegianphrases.ui.quiz

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.norwegianphrases.ActivityViewModel
import com.example.norwegianphrases.R
import com.example.norwegianphrases.database.Phrase
import com.example.norwegianphrases.databinding.QuizFragmentBinding
import kotlinx.android.synthetic.main.quiz_fragment.*

class QuizFragment : Fragment(), View.OnClickListener {

    private val viewModel: ActivityViewModel by activityViewModels()
    private var binding: QuizFragmentBinding? = null



    var phrases = listOf<Phrase>() // all phrases

    var quizList = arrayListOf<Phrase>() // 5 quizes
   // var noPhrases = listOf<Phrase>() // quiz alternatives / norwegian phrases
    var chTrans = listOf<Phrase>() // answer alternatives/ only chinese translations

    var mCurrentQuizPos: Int = 0
    var mSelectedOptionPos : Int = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = QuizFragmentBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        // get list of phrases from viewmodel
        phrases = viewModel.getAllPhrases()

        selectQuizes() // randomly select phrases from collection

        val firstQuiz = displayFirstQuiz()
        displayOptions(firstQuiz) // for first quiz

        binding!!.optionOne.setOnClickListener(this)
        binding!!.optionTwo.setOnClickListener(this)
        binding!!.optionThree.setOnClickListener(this)


        return root
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
    //display random phrase/quiz
    fun displayFirstQuiz() : Phrase {
        val quizPhrase: TextView = binding!!.quizPhrase
        quizPhrase.text = quizList[0].phrase
        return quizList[0]
    }

    // display answer options
    fun displayOptions(currentQuiz : Phrase) {
        val phraseList : ArrayList<Phrase> = arrayListOf() // collection of options
        phraseList.addAll(phrases)

        val optionsList : ArrayList<Phrase> = arrayListOf()
        optionsList.add(currentQuiz) // add currect option to list

        phraseList.remove(currentQuiz) // remove from options to avoid duplicates

        // add two randomly selected options to list
        for(i in 1..2) {
            val randomIndex = (phraseList.indices).random()
            val p = phraseList[randomIndex]
            optionsList.add(p)
            phraseList.remove(p)
        }

        val shuffledList = optionsList.shuffled()
        println("optionlist: ")
        for(p in optionsList) {
            println("::: "+p.chTrans)
        }
        println("shuffled optionlist: ")
        for(p in shuffledList) {
            println(":---:: "+p.chTrans)
        }

        val option1 = binding!!.optionOne
        val option2 = binding!!.optionTwo
        val option3 = binding!!.optionThree

        // set text
        option1.text = shuffledList[0].chTrans
        option2.text = shuffledList[1].chTrans
        option3.text = shuffledList[2].chTrans
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.option_one -> {
                selectedOptionView(option_one, 1)
            }
            R.id.option_two -> {
                selectedOptionView(option_two, 2)
            }
            R.id.option_three -> {
                selectedOptionView(option_three, 3)
            }
        }
    }

    fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {
        mSelectedOptionPos = selectedOptionNum
        tv.setTextColor(Color.parseColor("#FFBB86FC"))
        if(tv.text == quizList[mCurrentQuizPos].chTrans) {
            Toast.makeText(activity,"RRR",Toast.LENGTH_SHORT).show()
        } else if(tv.text != quizList[mCurrentQuizPos].chTrans) {
            Toast.makeText(activity,"false",Toast.LENGTH_SHORT).show()

        }
    }

}