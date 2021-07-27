package com.example.norwegianphrases.ui.quiz

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.norwegianphrases.R
import com.example.norwegianphrases.databinding.FillBlanksFragmentBinding
import com.example.norwegianphrases.databinding.QuizFragmentBinding

class FillBlanksFragment : Fragment() {

    private lateinit var fillBlanksViewModel: FillBlanksViewModel
    private var binding: FillBlanksFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fillBlanksViewModel =
            ViewModelProvider(this).get(FillBlanksViewModel::class.java)

        binding = FillBlanksFragmentBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
       // return inflater.inflate(R.layout.fill_blanks_fragment, container, false)
        return root
    }


}