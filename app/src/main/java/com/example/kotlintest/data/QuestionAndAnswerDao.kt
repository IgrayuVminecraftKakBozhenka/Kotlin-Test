package com.example.kotlintest.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface QuestionAndAnswerDao {

    @Query("SELECT * FROM questions")
    suspend fun readAllQuestionData(): List<QuestionModel>

    @Query("SELECT * FROM answers")
    suspend fun readAllAnswerData(): List<AnswersModel>

    @Query("SELECT answer FROM answers WHERE correct = 1")
    suspend fun getCorrectAnswer(): List<String>
}