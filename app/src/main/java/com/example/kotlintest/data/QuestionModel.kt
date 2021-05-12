package com.example.kotlintest.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class QuestionModel(
    @PrimaryKey
    val _id: Int,
    val question: String
)