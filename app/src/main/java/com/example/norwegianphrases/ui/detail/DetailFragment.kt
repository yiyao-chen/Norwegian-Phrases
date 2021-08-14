package com.example.norwegianphrases.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.norwegianphrases.ActivityViewModel
import com.example.norwegianphrases.R
import com.example.norwegianphrases.database.Phrase
import com.example.norwegianphrases.databinding.FragmentDetailBinding


class DetailFragment : Fragment(){
    private lateinit var appBarMenu: Menu

    private val viewModel: ActivityViewModel by activityViewModels() // shared viewmodel
    private var _binding: FragmentDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("----------DetailF.onCreateView")
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        //inflater.inflate(R.layout.fragment_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("-----------DetailF.onViewCreated")
        viewModel.clickedPhraseLiveData().observe(viewLifecycleOwner, {
            println("----DetailF.observe ")
            setText(it)
        })

    }

    /*
    // add fav-icon to appBar
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.appbar_menu, menu)
    }

     */

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorite -> {
                //item.setIcon(drawable/icon_favorite_border)
                println("favorite icon clicked")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        println("------DetailF.onDestroy")
        _binding = null
    }

    private fun setText(p : Phrase) {
        _binding?.phraseName?.text = "\""+ p.phrase + "\""
        _binding?.phraseTranslation?.text = "\"" + p.chTrans + "\""
        _binding?.noExplanation?.text = p.noExpl
        _binding?.chExplanation?.text = p.chExpl



    }

}