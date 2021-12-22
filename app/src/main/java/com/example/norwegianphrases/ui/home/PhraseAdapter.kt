package com.example.norwegianphrases.ui.home

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.norwegianphrases.R
import com.example.norwegianphrases.database.Phrase
import java.util.*
import kotlin.collections.ArrayList

class PhraseAdapter(
    private val listener: OnItemClickListener

    ): RecyclerView.Adapter<PhraseAdapter.ViewHolder>(), Filterable {

     var phrases = listOf<Phrase>()
    private var phrasesFull = listOf<Phrase>()

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
        holder.translation.text = current.chTrans
    }

    override fun getItemCount() = phrases.size

    fun updateAdapter(newList: List<Phrase>) {
        phrases = newList
        phrasesFull = ArrayList(newList)
    }

    interface OnItemClickListener {
        fun onItemClick(phraseClicked: Phrase)
    }

    override fun getFilter(): Filter {
        return phrasesFilter
    }

    private val phrasesFilter = object: Filter() {
        // filter data according to the constraint and return result
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList: MutableList<Phrase> = ArrayList()

            // if search field is empty, use the original list, else use filtered list
            if(constraint == null || constraint.isEmpty()) {
                filteredList.addAll(phrasesFull)
            } else {
                val filterPattern: String = constraint.toString().lowercase(Locale.ROOT).trim()
                for (item in phrasesFull) {
                    if (item.phrase != null) {

                        if (item.phrase.lowercase(Locale.ROOT).contains(filterPattern)) {
                            filteredList.add(item)
                        }
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            phrases = results?.values as List<Phrase>
            notifyDataSetChanged()
        }
    }


}