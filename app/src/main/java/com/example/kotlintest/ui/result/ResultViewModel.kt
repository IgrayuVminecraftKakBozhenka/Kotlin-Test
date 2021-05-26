package com.example.kotlintest.ui.result

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlintest.App
import com.example.kotlintest.data.QuestionAndAnswerDao
import com.example.kotlintest.data.QuestionAndAnswerDatabase
import com.example.kotlintest.ui.common.USER_ANSWERS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ResultViewModel(application: Application) : AndroidViewModel(application){

    private val correctAnswers = ArrayList<String>()
    private val userAnswers = ArrayList<String>()
    private val app = application
    private val dao = QuestionAndAnswerDatabase.getDatabase(app).dao()
    private var userScore = 0

    init {
        viewModelScope.launch {
            correctAnswers.addAll(withContext(Dispatchers.IO) {dao.getCorrectAnswer()})
        }
    }

    fun setUserAnswers(userAnswersFromView : ArrayList<String>) {
        userAnswers.addAll(userAnswersFromView)

    }

    private fun getScore() {
        userAnswers.forEach() { answer ->
            if (correctAnswers.contains(answer)) {
                userScore++
            }
        }
    }
}