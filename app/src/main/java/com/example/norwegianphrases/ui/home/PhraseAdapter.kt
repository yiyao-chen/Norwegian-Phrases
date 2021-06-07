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

class PhraseAdapter: RecyclerView.Adapter<PhraseAdapter.ViewHolder>(){

    private var phrases = listOf<Phrase>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.element_phrase, parent, false
        )
        return ViewHolder(itemView)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.phrase_name)
        val explanation: TextView = itemView.findViewById(R.id.phrase_explanation)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = phrases[position]

        holder.name.text = current.phrase
        holder.explanation.text = current.explanation
    }

    override fun getItemCount() = phrases.size

    fun updateAdapter(newList: List<Phrase>) {
        phrases = newList
        println("updated listsize.............." + phrases.size)

    }
}