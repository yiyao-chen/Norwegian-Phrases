package com.example.norwegianphrases.ui.home

import android.app.Application
import androidx.lifecycle.*
import com.example.norwegianphrases.database.AppDatabase
import com.example.norwegianphrases.database.PhraseDao
import com.example.norwegianphrases.database.Phrase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import kotlin.random.Random

class HomeViewModel(application : Application) : AndroidViewModel(application) {
    lateinit var db: AppDatabase
    private lateinit var phraseDao: PhraseDao
    lateinit var phrases: LiveData<List<Phrase>>

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun getDatabase() {
        db = AppDatabase.getDatabase(getApplication())
        phraseDao = db.phraseDao()
    }

    fun populateDatabase() {
        println("populate db")


        CoroutineScope(Dispatchers.IO).launch {
/*
            phraseDao.deleteAll()
            var phrase = Phrase(2,"pppp s", "exå: fdf")
            phraseDao.insert(phrase)
            phrase = Phrase(0," dasakø pp s", "expl: das")
            phraseDao.insert(phrase)
 */
            phraseDao.deleteAll()
            val lineList = mutableListOf<String>()
            val file = "test.txt"
            getApplication<Application>().assets.open(file).bufferedReader().forEachLine {
                var array = it.split(";")
                var phrase = array[0]
                var translation  = array[1]
                var phraseObj = Phrase(phrase, translation, array[2], array[3])
                phraseDao.insert(phraseObj)
                println(phraseObj.toString())
            }


        }

        phrases = phraseDao.getAll().asLiveData()
    }

    @JvmName("getPhrases1")
    fun getPhrases() = phrases
}