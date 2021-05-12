package com.example.kotlintest.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "answers")
class AnswersModel (
    @PrimaryKey(autoGenerate = true)
    val _id: Int,
    val question_id: Int,
    val answer: String,
    val correct: Boolean
)