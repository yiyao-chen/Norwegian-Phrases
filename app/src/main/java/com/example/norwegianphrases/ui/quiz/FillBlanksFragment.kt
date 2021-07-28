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
import androidx.lifecycle.ViewModelProvider
import com.example.norwegianphrases.databinding.FillBlanksFragmentBinding


class FillBlanksFragment : Fragment() {

    private lateinit var fillBlanksViewModel: FillBlanksViewModel
    private var binding: FillBlanksFragmentBinding? = null

    lateinit var answer: String
    var testPhrase: String = "Å holde tunga rett i munn"
    //lateinit var tvContent: TextView

    lateinit var firstPart: TextView
    lateinit var blankPart: EditText
    lateinit var lastPart: TextView



    //lateinit var content: SpannableStringBuilder
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fillBlanksViewModel =
            ViewModelProvider(this).get(FillBlanksViewModel::class.java)

        binding = FillBlanksFragmentBinding.inflate(inflater, container, false)
        val root: View = binding!!.root

        init()
        initCheckButton()

        return root
    }

    // display first sentence
    @SuppressLint("ClickableViewAccessibility")
    fun init() {
        firstPart = binding!!.phraseFirst
        lastPart = binding!!.phraseLast
        blankPart = binding!!.phraseBlank

        val array = testPhrase.split(" ")
        // the part after ____
        val subarray = array.slice(2..array.size-1)

        firstPart.text = array[0]
        lastPart.text  = subarray.toString()

        answer = array[1]
        println("answe:: "+answer)

        binding!!.layoutFillInBlank.setOnTouchListener(OnTouchListener { v, event ->
            println("touched")
            hideKeyBoard()
            true
        })
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
            }
        }
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