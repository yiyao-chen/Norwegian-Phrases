package com.example.norwegianphrases.ui.quiz

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
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

    lateinit var optionList: List<Phrase> // options for current quiz
    private var optionTvList = ArrayList<TextView>() // textviews

    private var binding: QuizFragmentBinding? = null
    lateinit var quizPhrase: TextView
    lateinit var mScore: TextView

    lateinit var option1 : TextView
    lateinit var option2 : TextView
    lateinit var option3 : TextView
    lateinit var progressbar: ProgressBar


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
/*
        for(p in quizList) {
            println("list p: " + p.phrase)
        }

 */

        quizPhrase = binding!!.quizPhrase
        mScore = binding!!.score
        option1 = binding!!.optionOne
        option2 = binding!!.optionTwo
        option3 = binding!!.optionThree

        optionTvList.add(option1)
        optionTvList.add(option2)
        optionTvList.add(option3)


        // progressbar
        progressbar = binding!!.progressBar
        progressbar.progress = quizViewModel.getCurrentQuizPos()
        binding!!.tvProgress.text = quizViewModel.getCurrentQuizPos().toString() + "/3"

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
        progressbar.progress = quizViewModel.getCurrentQuizPos()
        binding!!.tvProgress.text = quizViewModel.getCurrentQuizPos().toString() + "/3"

        return quizList[0]
    }

    // display answer options for the quiz
    fun displayOptions(currentQuiz : Phrase) {

        quizViewModel.defaultOptionsView(optionTvList) // clear color


        val shuffledList = quizViewModel.shuffleOptions(currentQuiz)
        optionList = shuffledList
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
                //update progressbar
                var progress = quizViewModel.getCurrentQuizPos()+1
                progressbar.progress = progress
                binding!!.tvProgress.text = progress.toString() + "/3"

                quizViewModel.increaseCurrentQuizPos()
                var index = quizViewModel.getCurrentQuizPos()



                if(index >= quizList.size) { // all quizes have been answered

                    // get score
                    quizViewModel.getScore().observe (viewLifecycleOwner, {
                        quizPhrase.text = "你答对了" + it.toString() + "道题"

                    })
                    binding!!.nextQuizButton.text = "finished"
                    binding!!.nextQuizButton.isClickable = false // disable next-button
                    setUnClickable() // disable options
                }else { //
                    println("else >>>>>> i: " + index)
                    nextQuiz()


                }
            }
        }
    }

    // disable option-buttons after selecting one option
    fun setUnClickable() {
        /*
        var options = ArrayList<TextView>()
        options.add(option_one)
        options.add(option_two)
        options.add(option_three)

         */
        for (o in optionTvList) {
            //o.setTextColor(Color.parseColor("#FF000000"))
            o.isClickable = false
            o.isEnabled = false
        }
    }


    fun nextQuiz() {
        /*
        var options = ArrayList<TextView>()
        options.add(option_one)
        options.add(option_two)
        options.add(option_three)

         */
        /*
        quizViewModel.increaseCurrentQuizPos()
        var index = quizViewModel.getCurrentQuizPos()

        if(index >= quizList.size) {
            println("size " +quizList.size )

            quizPhrase.text = "done"
            binding!!.nextQuizButton.isClickable = false // disable next-button
            setUnClickable() // disable options
        }else {
            println("else >>>>>> i: " + index)


         */


        var index = quizViewModel.getCurrentQuizPos()

            quizPhrase.text = quizList[index].phrase
            displayOptions(quizList[index])
        //}

        // remove icons
        for(o in optionTvList) {
            o.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}