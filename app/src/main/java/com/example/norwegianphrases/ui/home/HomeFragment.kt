package com.example.norwegianphrases.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.norwegianphrases.ActivityViewModel
import com.example.norwegianphrases.R
import com.example.norwegianphrases.database.Phrase
import com.example.norwegianphrases.databinding.FragmentHomeBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), PhraseAdapter.OnItemClickListener
{
    //private lateinit var homeViewModel: HomeViewModel
    private val viewModel : ActivityViewModel by activityViewModels()

    private var _binding: FragmentHomeBinding? = null
    private lateinit var mAdapter: PhraseAdapter
   // private var db: AppDatabase? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("-------------HomeFragment oncreate")


    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("--------HomeFragment onCreateView")

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("-----------HomeFragment onViewCreated")

        mAdapter = PhraseAdapter(this)

        recyclerView.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            adapter = mAdapter
        }

       // //val textView: TextView = binding.textHome

        // observe livedata in viewmodel and update adapter
        viewModel.getPhraseLive().observe(viewLifecycleOwner, { dataList->
            println("ViewModel.observe----")
            //textView.text = t

            mAdapter.updateAdapter(dataList)
            mAdapter.notifyDataSetChanged()
        })

        searchFilter()// search function

    }

    private fun searchFilter() {
        val searchView = search_bar
      //  searchView.imeOptions = EditorInfo.IME_ACTION_DONE

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String?): Boolean {
                println("submitted -----")
                mAdapter.updateAdapter(mAdapter.phrases)
                searchView.setQuery("", false)
                //search_bar.clearFocus()
                //search_bar.setFocusable(false)
                search_bar.isIconified = true
                return false
            }
            override fun onQueryTextChange(input: String?): Boolean {
                if (input != null) {
                    if(input.isNotEmpty()) {
                        mAdapter.filter.filter(input)
                        println("filtered : " + input)
                    }
                }
                return false
            }
        })
    }

    override fun onPause() {
        super.onPause()
        println("------------HomeFragment on pause")
        search_bar.setQuery("", false)
        search_bar.isIconified = true
    }

    override fun onDestroyView() {
        println("------------HomeF.onDestroyView")
        super.onDestroyView()
        _binding = null
        //search_bar.clearFocus()
        //activity?.let { println("?????"+it.isDestroyed) }
    }


    override fun onItemClick(phraseClicked: Phrase) {
        viewModel.setClickedPhrase(phraseClicked)
        findNavController().navigate(R.id.action_navigation_home_to_detailFragment)
    }
}