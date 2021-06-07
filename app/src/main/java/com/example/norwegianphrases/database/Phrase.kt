package com.example.norwegianphrases.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "phrases")
data class Phrase(
    @PrimaryKey(autoGenerate = true) val pid: Int,
    @ColumnInfo(name = "phrase") val phrase: String?,
)