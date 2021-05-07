package com.example.kotlintest.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class QuestionAndAnswerViewModel(application: Application): AndroidViewModel(application) {

    private val readAllQuestionData: LiveData<List<QuestionModel>>
    private val readAllAnswerData: LiveData<List<AnswersModel>>
    private val repository: QuestionAndAnswerRepository

    init {
        val questionAndAnswerDao = QuestionAndAnswerDatabase.getDatabase(application).dao()
        repository = QuestionAndAnswerRepository(questionAndAnswerDao)
        readAllQuestionData = repository.readAllQuestionData
        readAllAnswerData = repository.readAllAnswerData
    }
}