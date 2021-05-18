package com.example.kotlintest.data

import androidx.room.Dao
import androidx.room.Query
import com.example.kotlintest.data.models.AnswersModel
import com.example.kotlintest.data.models.QuestionModel

@Dao
interface QuestionAndAnswerDao {

    @Query("SELECT * FROM questions")
    suspend fun readAllQuestionData(): List<QuestionModel>

    @Query("SELECT * FROM answers")
    suspend fun readAllAnswerData(): List<AnswersModel>

    @Query("SELECT answer FROM answers WHERE correct = 1")
    suspend fun getCorrectAnswer(): List<String>
}