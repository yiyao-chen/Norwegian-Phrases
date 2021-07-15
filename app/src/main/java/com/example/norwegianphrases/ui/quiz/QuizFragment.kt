package com.example.norwegianphrases.ui.quiz

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.norwegianphrases.ActivityViewModel
import com.example.norwegianphrases.database.Phrase
import com.example.norwegianphrases.databinding.QuizFragmentBinding

class QuizFragment : Fragment() {

    private val viewModel: ActivityViewModel by activityViewModels()
    private var binding: QuizFragmentBinding? = null

    var phrases = listOf<Phrase>() // all phrases

    var quizList = arrayListOf<Phrase>() // 5 quizes
   // var noPhrases = listOf<Phrase>() // quiz alternatives / norwegian phrases
    var chTrans = listOf<Phrase>() // answer alternatives/ only chinese translations

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

        for(p in quizList) {
            println("::: "+p.phrase)
        }

        displayFirstQuiz()
        displayOptions() // for first quiz
        return root
    }

    fun selectQuizes() {
        val phraseList : ArrayList<Phrase> = arrayListOf() // collection of options
        phraseList.addAll(phrases)

        for(i in 1..2) {
            val randomIndex = (phraseList.indices).random()
            // add randomly selected quiz to quizList, and remove from collection to avoid duplicates
            val p = phraseList[randomIndex]
            quizList.add(p)
            phraseList.remove(p)
        }


    }
    //display random phrase/quiz
    fun displayFirstQuiz() {
        val quizPhrase: TextView = binding!!.quizPhrase
        quizPhrase.text = quizList[0].phrase

    }
    // display answer options
    fun displayOptions() {
        val phraseList : ArrayList<Phrase> = arrayListOf() // collection of options
        phraseList.addAll(phrases)

        val option1 = binding!!.optionOne
        val option2 = binding!!.optionTwo
        val option3 = binding!!.optionThree

        // assign correct answer to option 1
        option1.text = quizList[0].chTrans

        // assign random values to option 2
        phraseList.remove(quizList[0]) // remove the correct option from collection to avoid duplicates
            var randomIndex = (phraseList.indices).random()
            // add randomly selected quiz as option, and remove from collection to avoid duplicates
            var p = phraseList[randomIndex]
            option2.text = p.chTrans
            phraseList.remove(p)

        // assign random values to option 3
        randomIndex = (phraseList.indices).random()
        // add randomly selected quiz as option, and remove from collection to avoid duplicates
        p = phraseList[randomIndex]
        option3.text = p.chTrans
        phraseList.remove(p)



    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}