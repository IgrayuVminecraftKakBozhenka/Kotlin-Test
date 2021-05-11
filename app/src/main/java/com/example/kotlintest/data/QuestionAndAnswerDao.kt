package com.example.kotlintest.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface QuestionAndAnswerDao {

    @Insert
    suspend fun addQuestion(question: QuestionModel)

    @Insert
    suspend fun addAnswer(answer: AnswersModel)

    @Query("SELECT * FROM questions")
    suspend fun readAllQuestionData(): List<QuestionModel>

    @Query("SELECT * FROM answers")
    suspend fun readAllAnswerData(): List<AnswersModel>
}