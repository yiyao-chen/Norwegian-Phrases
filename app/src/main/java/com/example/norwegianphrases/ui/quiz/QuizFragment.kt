package com.example.norwegianphrases.ui.quiz

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.norwegianphrases.R
import com.example.norwegianphrases.databinding.QuizFragmentBinding
import com.example.norwegianphrases.ui.notifications.NotificationsViewModel

class QuizFragment : Fragment() {

    companion object {
        fun newInstance() = QuizFragment()
    }

    private lateinit var viewModel: QuizViewModel
    private var binding: QuizFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProvider(this).get(QuizViewModel::class.java)

        binding = QuizFragmentBinding.inflate(inflater, container, false)
        val root: View = binding!!.root

        //display quiz
        val quizPhrase: TextView = binding!!.quizPhrase
        viewModel.quiz.observe(viewLifecycleOwner, {
            quizPhrase.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}