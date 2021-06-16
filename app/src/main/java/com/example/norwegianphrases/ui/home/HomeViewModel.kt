package com.example.norwegianphrases.ui.home

import android.app.Application
import androidx.lifecycle.*
import com.example.norwegianphrases.database.AppDatabase
import com.example.norwegianphrases.database.PhraseDao
import com.example.norwegianphrases.database.Phrase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(application : Application) : AndroidViewModel(application) {
    lateinit var db: AppDatabase
    private lateinit var phraseClicked: Phrase
    private var phraseLive = MutableLiveData<Phrase>()

    private lateinit var phraseDao: PhraseDao
    lateinit var phrases: LiveData<List<Phrase>>

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun getDatabase(): AppDatabase {
        db = AppDatabase.getDatabase(getApplication())
        phraseDao = db.phraseDao()
        return db
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
            val file = "test.txt"
            getApplication<Application>().assets.open(file).bufferedReader().forEachLine {
                val array = it.split(";")
                val phrase = array[0].trim()
                val translation  = array[1].trim()

                var phraseObj: Phrase? = null
                if(array.size == 3) {
                    phraseObj = Phrase(phrase, translation, array[2].trim(), null)
                }
                if(array.size == 4) {
                    phraseObj = Phrase(phrase, translation, array[2].trim(), array[3].trim())
                }
                phraseDao.insert(phraseObj)
            }
        }
        phrases = phraseDao.getAll().asLiveData()
    }

    @JvmName("getPhrases1")
    fun getPhrases() = phrases

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