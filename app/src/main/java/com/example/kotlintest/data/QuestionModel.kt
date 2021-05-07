package com.example.kotlintest.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class QuestionModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val question: String
)