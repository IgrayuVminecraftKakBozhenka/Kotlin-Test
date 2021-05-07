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
    fun readAllQuestionData(): LiveData<List<QuestionModel>>

    @Query("SELECT * FROM answers")
    fun readAllAnswerData(): LiveData<List<AnswersModel>>
}