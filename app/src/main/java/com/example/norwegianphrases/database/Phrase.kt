package com.example.norwegianphrases.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

class Phrase(
    val id:String? = null,
    val phrase: String? = null,
    val noExpl: String? = null,
    val chTrans: String? = null,
    val chExpl: String? = null
)
