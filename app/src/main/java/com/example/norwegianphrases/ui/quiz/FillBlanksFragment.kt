package com.example.norwegianphrases.ui.quiz

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.TextView
import com.example.norwegianphrases.R
import com.example.norwegianphrases.databinding.FillBlanksFragmentBinding
import com.example.norwegianphrases.databinding.QuizFragmentBinding

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

        return root
    }

    fun init() {
        firstPart = binding!!.phraseFirst
        lastPart = binding!!.phraseLast
        blankPart = binding!!.phraseBlank

        val array = testPhrase.split(" ")
        val subarray = array.slice(2..array.size-1)

        firstPart.text = array[0]
        lastPart.text  = subarray.toString()
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