package com.example.kotlintest.data

import androidx.lifecycle.LiveData

class QuestionAndAnswerRepository(private val questionAndAnswerDao: QuestionAndAnswerDao) {

    val readAllQuestionData: LiveData<List<QuestionModel>> = questionAndAnswerDao.readAllQuestionData()

    val readAllAnswerData: LiveData<List<AnswersModel>> = questionAndAnswerDao.readAllAnswerData()

    suspend fun addQuestion(question: QuestionModel) {
        questionAndAnswerDao.addQuestion(question)
    }

    suspend fun addAnswer(answer: AnswersModel) {
        questionAndAnswerDao.addAnswer(answer)
    }
}