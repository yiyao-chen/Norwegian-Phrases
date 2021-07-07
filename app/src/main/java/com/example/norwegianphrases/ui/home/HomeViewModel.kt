package com.example.norwegianphrases.ui.home

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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(application : Application) : AndroidViewModel(application) {
    val database = Firebase.database("https://norwegian-phrases-default-rtdb.europe-west1.firebasedatabase.app/")

    private lateinit var phraseClicked: Phrase
    private var phraseLive = MutableLiveData<Phrase>()

     var phrases: MutableLiveData<List<Phrase>> = MutableLiveData()


     fun readFromDatabase() {
        val myRef = database.getReference("phrases")
         var tempList: ArrayList<Phrase> = arrayListOf()

         // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(e in snapshot.children) {
                    val p = e.getValue(Phrase::class.java)
                    tempList.add(p!!)
                    println("--------size--------------- " + tempList.size)
                }

                for(p in tempList) {

                    println("......mmmmmmmmmm. " + p.toString())
                }

                notifyListLiveData(tempList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }
        })
    }
    fun getList() = phrases

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