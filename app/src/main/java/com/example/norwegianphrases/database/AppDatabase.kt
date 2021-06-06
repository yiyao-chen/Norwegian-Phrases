package com.example.norwegianphrases.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Phrase::class), version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun phraseDao(): PhraseDao


}