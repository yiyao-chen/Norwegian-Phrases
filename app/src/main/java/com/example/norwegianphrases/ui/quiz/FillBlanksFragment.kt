package com.example.norwegianphrases.ui.quiz

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.norwegianphrases.ActivityViewModel
import com.example.norwegianphrases.database.Phrase
import com.example.norwegianphrases.databinding.FillBlanksFragmentBinding

// not used
class FillBlanksFragment : Fragment() {

    private val activityViewModel: ActivityViewModel by activityViewModels()
    private lateinit var fillBlanksViewModel: FillBlanksViewModel
    private var binding: FillBlanksFragmentBinding? = null

    private var allPhrases = listOf<Phrase>() // all phrases
    var quizList = arrayListOf<Phrase>() // 5 quizes

    lateinit var currentQuiz: Phrase //current quiz
    var currentPos: Int = 0 // counter for quiz-position in quizList

    lateinit var answer: String

    lateinit var firstPart: TextView
    lateinit var blankPart: EditText
    lateinit var lastPart: TextView

    lateinit var chTranslation: TextView
    lateinit var noExplanation: TextView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fillBlanksViewModel =
            ViewModelProvider(this).get(FillBlanksViewModel::class.java)

        binding = FillBlanksFragmentBinding.inflate(inflater, container, false)
        val root: View = binding!!.root

        initView()
        initCheckButton()
        initNextButton()

        return root
    }

    // initialize views and button onclick functions when user takes quiz for the first time
    @SuppressLint("ClickableViewAccessibility")
    fun initView() {
        getPhrasesFromDb(activityViewModel.getAllPhrases()) // get list of phrases from database

        selectQuizes() // randomly select 5 phrases from collection as questions

        firstPart = binding!!.phraseFirst
        lastPart = binding!!.phraseLast
        blankPart = binding!!.phraseBlank

        chTranslation = binding!!.chTranslation
        noExplanation = binding!!.noExplanation

        setQuiz()
/*
        binding!!.fillblankScrollview.setOnTouchListener { v, event ->
            println("scrollview touched")
            hideKeyBoard()
            true
        }
        binding!!.fillblankScrollview.setOnClickListener {
            println("scrollview clicked")
        }


 */

        // when user has keyboard on, click outside should hide the keyboard
        binding!!.layoutFillInBlank.setOnTouchListener { v, event ->
            println("linearlayout touched")
            hideKeyBoard()
            true
        }
        binding!!.layoutFillInBlank.setOnClickListener {
            println("ll clicked")
        }

        binding!!.summary.setOnTouchListener { v, event ->
            println("s touched")
            hideKeyBoard()
            true
        }



/*
        blankPart.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                // If the event is a key-down event on the "enter" button
                if (event.getAction() === KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    // Perform action on key press
                        println("enter")
                    return true
                }
                return false
            }
        })


 */


    }

    // set views back when user chooses to do quiz again after finishing a set of quizes
    private fun resetView() {
        firstPart.setVisibility(View.VISIBLE)
        blankPart.setVisibility(View.VISIBLE)
        lastPart.setVisibility(View.VISIBLE)
        binding!!.chFlag.setVisibility(View.VISIBLE)
        binding!!.noFlag.setVisibility(View.VISIBLE)

        chTranslation.setVisibility(View.VISIBLE)
        noExplanation.setVisibility(View.VISIBLE)

        currentPos = 0
        quizList.clear()
        selectQuizes()
        setQuiz()
    }


    private fun getPhrasesFromDb(list : List<Phrase>) {
        allPhrases = list
        currentQuiz = allPhrases[currentPos]
    }

    // randomly select 3 phrases from phrasesList and add to global quizList
    private fun selectQuizes() {
       // score.value = 0

        val phraseList : ArrayList<Phrase> = arrayListOf() // collection of options
        phraseList.addAll(allPhrases)

        for(i in 1..2) {
            val randomIndex = (phraseList.indices).random()
            // add randomly selected quiz to quizList, and remove from collection to avoid duplicates
            val p = phraseList[randomIndex]
            quizList.add(p)
            phraseList.remove(p)
        }

        for(p in quizList) {
            println("list p: " + p.phrase)
        }
    }




    private fun hideKeyBoard() {
        val view = activity?.currentFocus
        if (view != null) {
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun initCheckButton() {
        binding!!.check.setOnClickListener() {
            val input = blankPart.text.toString()
            hideKeyBoard()

            if(input.trim().lowercase() == answer.lowercase()) { // show next-btn if answer is correct
                println(" ..." + true)
                Toast.makeText(activity, "回答正确", Toast.LENGTH_SHORT).show()

                binding!!.btnNextFillBlank.setVisibility(View.VISIBLE)
            } else {
                Toast.makeText(activity, "回答错误", Toast.LENGTH_SHORT).show()
                println(" ..." + false)
            }

            if ( currentPos == quizList.size) {
                binding!!.check.text = "提交"
                binding!!.btnNextFillBlank.text = "下一题"
                binding!!.btnNextFillBlank.setVisibility(View.GONE)

                resetView()

                println("currentPos == quizList.size")




            }
        }
    }

    fun initNextButton() {

        binding!!.btnNextFillBlank.setOnClickListener() {
            currentPos++
            println("pos: " + currentPos)

            if(currentPos < quizList.size) {
                setQuiz() // set next quiz
                binding!!.btnNextFillBlank.setVisibility(View.GONE) // hide NEXT-btn

            } else if (currentPos == quizList.size) { // has gone through all quizes
                println("finished")
                //hide views
                firstPart.setVisibility(View.GONE)
                blankPart.setVisibility(View.GONE)
                lastPart.setVisibility(View.GONE)
                binding!!.chFlag.setVisibility(View.GONE)
                binding!!.noFlag.setVisibility(View.GONE)

                chTranslation.setVisibility(View.GONE)
                noExplanation.setVisibility(View.GONE)

                binding!!.check.setVisibility(View.GONE)
                binding!!.check.isClickable = false
                binding!!.btnNextFillBlank.text = "退出"

                var sum = ""
                for(p in quizList) {
                    sum+= p.phrase + "\n"
                }
                binding!!.summary.text = sum

            } else if (currentPos > quizList.size) { // when user clicks "tui chu"
                findNavController().navigate(com.example.norwegianphrases.R.id.action_fillBlanksFragment_to_navigation_dashboard)
            }

        }
    }

    // display quiz
    fun setQuiz() {
        blankPart.text.clear()

        val array = quizList[currentPos].phrase?.split(" ")    // phrase in str-array format

        println("set quiz : " +  array.toString())
        val subarray = array?.slice(2 until array.size)     // the part after ____

        firstPart.text = array!![0]
        lastPart.text  = subarray.toString()

        answer = array[1]
        println("answer:: "+answer)

        chTranslation.text = quizList[currentPos].chTrans
        noExplanation.text = quizList[currentPos].noExpl
    }

}