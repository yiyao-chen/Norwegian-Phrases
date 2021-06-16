package com.example.norwegianphrases.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PhraseDao {
    @Query("SELECT * FROM phrases")
    fun getAll(): Flow<List<Phrase>>

    @Query("SELECT * FROM phrases WHERE pid IN (:phraseIds)")
    fun loadAllByIds(phraseIds: IntArray): Flow<List<Phrase>>

    @Query("SELECT * FROM phrases WHERE phrase LIKE '%' || :text || '%'")
    fun getPhrase(text: String) : Phrase

    @Insert
    fun insertAll(vararg phrases: Phrase)

    @Insert
    fun insert(phrase: Phrase?)

    @Delete
    fun delete(phrase: Phrase)

    @Query("DELETE FROM phrases")
    fun deleteAll()

    @Delete
    fun deletePhrases(vararg phrases: Phrase)
}