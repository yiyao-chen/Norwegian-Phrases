package com.example.norwegianphrases.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "phrases")
data class Phrase(
    @ColumnInfo(name = "phrase") val phrase: String?,
    @ColumnInfo(name = "translation") val translation: String?,
    @ColumnInfo(name = "no_explanation") val no_explanation: String?,
    @ColumnInfo(name = "ch_explanation") val ch_explanation: String?,
    @PrimaryKey(autoGenerate = true) val pid: Int = 0
)