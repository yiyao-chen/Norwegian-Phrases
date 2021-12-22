package com.example.norwegianphrases.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.norwegianphrases.R
import com.example.norwegianphrases.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // set text to textViews and buttons
        initViews()

        // set onClickListener on buttons
        setButtonFunction()


        return root
    }

    private fun initViews() {
        val textView: TextView = binding.headerQuizfragment
        dashboardViewModel.instruction.observe(viewLifecycleOwner, {
            textView.text = it
        })

        binding.btnMultipleChoice.text = "开始"

    }

    fun setButtonFunction() {
        // quiz type 1: multiple choice
        val button: Button = binding.btnMultipleChoice
        button.setOnClickListener() {
            findNavController().navigate(R.id.action_navigation_dashboard_to_quizFragment)

        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}