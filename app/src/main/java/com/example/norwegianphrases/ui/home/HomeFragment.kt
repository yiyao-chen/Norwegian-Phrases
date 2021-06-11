package com.example.norwegianphrases.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.norwegianphrases.R
import com.example.norwegianphrases.database.Phrase
import com.example.norwegianphrases.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), PhraseAdapter.OnItemClickListener
{

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private lateinit var mAdapter: PhraseAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeViewModel.getDatabase()
        homeViewModel.populateDatabase()
        println("view created")
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        homeViewModel.getPhrases().observe(viewLifecycleOwner, { dataList->
            println("observe----")
            println("size----" + dataList.size)

            var t = ""
            for(p in dataList) {
                t = t + " ; "+ p.toString()
            }
            //textView.text = t

            mAdapter.updateAdapter(dataList)
            mAdapter.notifyDataSetChanged()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(phraseClicked: Phrase) {
        homeViewModel.setClickedPhrase(phraseClicked)
        findNavController().navigate(R.id.action_navigation_home_to_detailFragment)
    }
}