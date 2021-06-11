package com.example.norwegianphrases.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.norwegianphrases.database.Phrase
import com.example.norwegianphrases.databinding.FragmentDetailBinding
import com.example.norwegianphrases.ui.home.HomeViewModel

class DetailFragment : Fragment(){

    companion object {
        fun newInstance() = DetailFragment()
    }

    private lateinit var viewModel: HomeViewModel // shared viewmodel
    private var _binding: FragmentDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        //inflater.inflate(R.layout.fragment_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("o v c")

        viewModel.clickedPhraseLiveData().observe(viewLifecycleOwner, {
            println("observe ")
            setText(it)
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setText(p : Phrase) {
        println("set tetx")
        _binding?.phraseName?.text = p.phrase
        _binding?.phraseTranslation?.text = p.translation
        _binding?.noExplanation?.text = p.no_explanation
        _binding?.chExplanation?.text = p.ch_explanation


    }

}