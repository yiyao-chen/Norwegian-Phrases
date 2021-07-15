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
import com.example.norwegianphrases.ui.notifications.NotificationsViewModel
import kotlinx.android.synthetic.main.quiz_fragment.*

class QuizFragment : Fragment(), View.OnClickListener {

    private val activityViewModel: ActivityViewModel by activityViewModels()
    private lateinit var quizViewModel: QuizViewModel
    var quizList = arrayListOf<Phrase>() // 5 quizes

    private var binding: QuizFragmentBinding? = null
    lateinit var quizPhrase: TextView


    // var phrases = listOf<Phrase>() // all phrases

//    var quizList = arrayListOf<Phrase>() // 5 quizes


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        quizViewModel =
            ViewModelProvider(this).get(QuizViewModel::class.java)

        binding = QuizFragmentBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        // get list of phrases from viewmodel
       // phrases = activityViewModel.getAllPhrases()

        // get list of phrases from activityviewmodel and store in quizviewmodel
        quizViewModel.setPhrases(activityViewModel.getAllPhrases())

        quizViewModel.selectQuizes() // randomly select phrases from collection
        quizList = quizViewModel.getQuizList()

        for(p in quizList) {
            println("list p: " + p.phrase)
        }
        quizPhrase = binding!!.quizPhrase

        val firstQuiz = displayFirstQuiz()
        displayOptions(firstQuiz) // for first quiz


        binding!!.optionOne.setOnClickListener(this)
        binding!!.optionTwo.setOnClickListener(this)
        binding!!.optionThree.setOnClickListener(this)

        binding!!.nextQuizButton.setOnClickListener(this)


        return root
    }


    //display random phrase/quiz
    fun displayFirstQuiz() : Phrase {


        quizPhrase.text = quizList[0].phrase
        return quizList[0]
    }

    // display answer options
    fun displayOptions(currentQuiz : Phrase) {

        val shuffledList = quizViewModel.shuffleOptions(currentQuiz)

        val option1 = binding!!.optionOne
        val option2 = binding!!.optionTwo
        val option3 = binding!!.optionThree

        // set text
        option1.text = shuffledList[0].chTrans
        option2.text = shuffledList[1].chTrans
        option3.text = shuffledList[2].chTrans
    }



    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.option_one -> {
                quizViewModel.changeSelectedOptionView(option_one, 1)
            }
            R.id.option_two -> {
                quizViewModel.changeSelectedOptionView(option_two, 2)
            }
            R.id.option_three -> {
                quizViewModel.changeSelectedOptionView(option_three, 3)
            }
            R.id.next_quiz_button -> {
                println("button c: ")

                nextQuiz()
            }
        }
    }

    fun nextQuiz() {
        quizViewModel.increaseCurrentQuizPos()
        var index = quizViewModel.getCurrentQuizPos()

        println("index now: " + index)

        if(index >= quizList.size) {
            println("size " +quizList.size )

            println(">>>>>>")
            quizPhrase.text = "done"
            binding!!.nextQuizButton.isClickable = false
        }else {
            println("else >>>>>> i: " + index)

            quizPhrase.text = quizList[index].phrase
            displayOptions(quizList[index])
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}