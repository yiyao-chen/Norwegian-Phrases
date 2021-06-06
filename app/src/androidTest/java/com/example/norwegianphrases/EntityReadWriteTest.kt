package com.example.norwegianphrases

import android.content.Context
import androidx.room.Room

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.norwegianphrases.database.AppDatabase
import com.example.norwegianphrases.database.Phrase
import com.example.norwegianphrases.database.PhraseDao
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class EntityReadWriteTest {
    private lateinit var phraseDao: PhraseDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()
        phraseDao = db.phraseDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writePhraseAndRead() {
        val phrase = Phrase(2,"pppp s")
        phraseDao.insert(phrase)
        val res = phraseDao.getPhrase("ppp ")
        assertEquals(phrase, res)
    }
}