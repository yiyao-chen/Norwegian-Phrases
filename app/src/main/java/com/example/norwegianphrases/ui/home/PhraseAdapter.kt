package com.example.norwegianphrases.ui.home

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.norwegianphrases.R
import com.example.norwegianphrases.database.Phrase

class PhraseAdapter(
    private val listener: OnItemClickListener

    ): RecyclerView.Adapter<PhraseAdapter.ViewHolder>(){

    private var phrases = listOf<Phrase>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.element_phrase, parent, false
        )
        return ViewHolder(itemView)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }
        val name: TextView = itemView.findViewById(R.id.phrase_name)
        val translation: TextView = itemView.findViewById(R.id.phrase_translation)
       // val no_explanation: TextView = itemView.findViewById(R.id.no_explanation)
        //val ch_explanation: TextView = itemView.findViewById(R.id.ch_explanation)

        // click item, send item to method .onItemClick in HomeFragment
        override fun onClick(v: View?) {
            val position: Int = absoluteAdapterPosition
            val phraseClicked = phrases[position]
            listener.onItemClick(phraseClicked)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = phrases[position]

        holder.name.text = current.phrase
        holder.translation.text = current.translation
        //holder.no_explanation.text = current.no_explanation
        //holder.ch_explanation.text = current.ch_explanation

    }

    override fun getItemCount() = phrases.size

    fun updateAdapter(newList: List<Phrase>) {
        phrases = newList
        println("updated listsize.............." + phrases.size)

    }

    interface OnItemClickListener {
        fun onItemClick(phraseClicked: Phrase)
    }
}