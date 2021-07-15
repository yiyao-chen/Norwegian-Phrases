package com.example.norwegianphrases

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.*
import com.example.norwegianphrases.database.Phrase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class ActivityViewModel(application : Application) : AndroidViewModel(application) {
    val database = Firebase.database("https://norwegian-phrases-default-rtdb.europe-west1.firebasedatabase.app/")

    private lateinit var phraseClicked: Phrase
    private var phraseLive = MutableLiveData<Phrase>() // the selected phrase?

     var phrases: MutableLiveData<List<Phrase>> = MutableLiveData()
    var phrasesFull: ArrayList<Phrase> = arrayListOf()


     fun readFromDatabase() {
        val myRef = database.getReference("phrases")
         println("read from database")
         phrasesFull.clear()

         // Read from the database
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(e in snapshot.children) {
                    val p = e.getValue(Phrase::class.java)
                    phrasesFull.add(p!!)
                    println("--------size--------------- " + phrasesFull.size)
                }

                for(p in phrasesFull) {
                    println("......mmmmmmmmmm. " + p.toString())
                }

                notifyListLiveData(phrasesFull)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }
        })
    }
    fun getPhraseLive() = phrases

    fun getAllPhrases() = phrasesFull

    fun notifyListLiveData(list: MutableList<Phrase>){
        phrases.value = list
    }

    fun setClickedPhrase(phrase: Phrase) {
        phraseClicked = phrase
        notifyLiveData(phrase)
    }

    private fun notifyLiveData(phrase: Phrase) {
        phraseLive.value = phrase
        println("notified : phrase = " + phrase.toString())
    }

    fun clickedPhraseLiveData() = phraseLive as LiveData<Phrase>
}