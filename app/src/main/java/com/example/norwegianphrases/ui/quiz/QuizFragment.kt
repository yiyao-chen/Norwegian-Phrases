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
    lateinit var mScore: TextView

    lateinit var option1 : TextView
    lateinit var option2 : TextView
    lateinit var option3 : TextView


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
        mScore = binding!!.score
        option1 = binding!!.optionOne
        option2 = binding!!.optionTwo
        option3 = binding!!.optionThree


        val firstQuiz = displayFirstQuiz()
        displayOptions(firstQuiz) // for first quiz

       // mScore.text = "Poeng: " + 0
        quizViewModel.getScore().observe (viewLifecycleOwner, {
            mScore.text = it.toString()
        })

        binding!!.optionOne.setOnClickListener(this)
        binding!!.optionTwo.setOnClickListener(this)
        binding!!.optionThree.setOnClickListener(this)
        binding!!.nextQuizButton.setOnClickListener(this)

        return root
    }

    //display random phrase/quiz
    fun displayFirstQuiz() : Phrase {
        quizPhrase.text = quizList[0].phrase

        println("text color::::::: "+quizPhrase.textColors)
        return quizList[0]
    }

    // display answer options
    fun displayOptions(currentQuiz : Phrase) {

        val shuffledList = quizViewModel.shuffleOptions(currentQuiz)



        // set text
        option1.text = shuffledList[0].chTrans
        option2.text = shuffledList[1].chTrans
        option3.text = shuffledList[2].chTrans
    }



    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.option_one -> {
                setUnClickable()
                quizViewModel.changeSelectedOptionView(option_one, 1)

            }
            R.id.option_two -> {
                setUnClickable()

                quizViewModel.changeSelectedOptionView(option_two, 2)

            }
            R.id.option_three -> {
                setUnClickable()
                quizViewModel.changeSelectedOptionView(option_three, 3)

            }
            R.id.next_quiz_button -> {
                nextQuiz()


            }
        }
    }

    // disable option-buttons after selecting one option
    fun setUnClickable() {
        var options = ArrayList<TextView>()
        options.add(option_one)
        options.add(option_two)
        options.add(option_three)

        for (o in options) {
            //o.setTextColor(Color.parseColor("#FF000000"))
            o.isClickable = false
            o.isEnabled = false

        }
    }


    fun nextQuiz() {
        var options = ArrayList<TextView>()
        options.add(option_one)
        options.add(option_two)
        options.add(option_three)

        quizViewModel.defaultOptionsView(options) // clear color

        quizViewModel.increaseCurrentQuizPos()
        var index = quizViewModel.getCurrentQuizPos()

        if(index >= quizList.size) {
            println("size " +quizList.size )

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