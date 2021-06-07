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
    private lateinit var phraseDao: PhraseDao
    lateinit var phrases: LiveData<List<Phrase>>

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun createDatabase() {
        db = AppDatabase.getDatabase(getApplication())
        phraseDao = db.phraseDao()
        CoroutineScope(Dispatchers.IO).launch {

            phraseDao.deleteAll()
            var phrase = Phrase(2,"pppp s")
            phraseDao.insert(phrase)
            phrase = Phrase(0," dasakø pp s")
            phraseDao.insert(phrase)
        }

        phrases = phraseDao.getAll().asLiveData()
    }

    @JvmName("getPhrases1")
    fun getPhrases() = phrases
}