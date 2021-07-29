package com.example.norwegianphrases.ui.quiz

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.norwegianphrases.ActivityViewModel
import com.example.norwegianphrases.database.Phrase
import com.example.norwegianphrases.databinding.FillBlanksFragmentBinding


class FillBlanksFragment : Fragment() {

    private val activityViewModel: ActivityViewModel by activityViewModels()
    private lateinit var fillBlanksViewModel: FillBlanksViewModel
    private var binding: FillBlanksFragmentBinding? = null

    private var allPhrases = listOf<Phrase>() // all phrases
    var quizList = arrayListOf<Phrase>() // 5 quizes

    lateinit var currentQuiz: Phrase //current quiz
    var currentPos: Int = 0 // counter for quiz-position in quizList

    lateinit var answer: String
    var testPhrase: String = "Å holde tunga rett i munn"
    //lateinit var tvContent: TextView

    lateinit var firstPart: TextView
    lateinit var blankPart: EditText
    lateinit var lastPart: TextView

    lateinit var chTranslation: TextView
    lateinit var noExplanation: TextView



    //lateinit var content: SpannableStringBuilder
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

    // display first sentence and initialize button onclick functions
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

        binding!!.layoutFillInBlank.setOnTouchListener { v, event ->
            println("touched")
            hideKeyBoard()
            true
        }
    }


    fun getPhrasesFromDb(list : List<Phrase>) {
        allPhrases = list
        currentQuiz = allPhrases[currentPos]
    }

    // randomly select 3 phrases from phrasesList and add to global quizList
    fun selectQuizes() {
       // score.value = 0

        val phraseList : ArrayList<Phrase> = arrayListOf() // collection of options
        phraseList.addAll(allPhrases)

        for(i in 1..3) {
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

    fun initCheckButton() {
        binding!!.check.setOnClickListener() {
            val input = blankPart.text.toString()
            println("input: " + input)
            hideKeyBoard()

            if(input == answer) {
                println(" ..." + true)
                binding!!.btnNextFillBlank.setVisibility(View.VISIBLE)
            } else {
                println(" ..." + false)

            }
        }
    }

    fun initNextButton() {

        binding!!.btnNextFillBlank.setOnClickListener() {
            currentPos++
            println("pos: " + currentPos)

            if(currentPos < quizList.size) {
                setQuiz() // set next quiz
                // hide NEXT-btn
                binding!!.btnNextFillBlank.setVisibility(View.GONE)

            } else {
                println("finished")
                //hide views
                binding!!.phraseFirst.setVisibility(View.GONE)
                binding!!.phraseBlank.setVisibility(View.GONE)
                binding!!.phraseLast.setVisibility(View.GONE)

                chTranslation.setVisibility(View.GONE)
                noExplanation.setVisibility(View.GONE)

                binding!!.check.setVisibility(View.GONE)
                binding!!.btnNextFillBlank.setVisibility(View.GONE)
            }

        }
    }

    // display quiz
    fun setQuiz() {
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

/*
    fun niiit() {
        tvContent = binding!!.phrase

        content = SpannableStringBuilder(testPhrase)
        answer = testPhrase.split(" ")[1]
        println("answer: " + answer)
        val s:String = "hei ___ das"
        val colorSpan: ForegroundColorSpan = ForegroundColorSpan(Color.GREEN)
        val ss: SpannableString = SpannableString(testPhrase)


        ss.setSpan(colorSpan, 2, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tvContent.setText(ss)

        val clickableSpan: ClickableSpan = object: ClickableSpan() {
            override fun onClick(v: View) {
                println("span clicked")
                val view: View = LayoutInflater.from(context).inflate(R.layout.popup_input, null)

               // val popupWindow = PopupWindow(view, LayoutParams.MATCH_PARENT, dp2px(40))
            }

        }

        ss.setSpan(clickableSpan, 3,12,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        // activate click-method
        tvContent.setMovementMethod(LinkMovementMethod.getInstance())
        tvContent.setText(ss)

    }


 */

}