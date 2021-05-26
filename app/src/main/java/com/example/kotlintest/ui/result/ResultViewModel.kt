package com.example.kotlintest.ui.result

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kotlintest.App
import com.example.kotlintest.R
import com.example.kotlintest.data.QuestionAndAnswerDao
import com.example.kotlintest.data.QuestionAndAnswerDatabase
import com.example.kotlintest.ui.common.USER_ANSWERS
import com.example.kotlintest.ui.result.models.ResultPageModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ResultViewModel(application: Application) : AndroidViewModel(application){

    private val correctAnswers = ArrayList<String>()
    private val userAnswers = ArrayList<String>()
    private val app = application
    private val dao = QuestionAndAnswerDatabase.getDatabase(app).dao()
    private var userScore = 0
    private var userLevel = ""
    val resultPageModel : MutableLiveData<ResultPageModel> = MutableLiveData()

    fun setUserAnswers(userAnswersFromView : ArrayList<String>) {
        userAnswers.addAll(userAnswersFromView)
        viewModelScope.launch {
            val answers = (withContext(Dispatchers.IO) {dao.getCorrectAnswer()})
            correctAnswers.addAll(answers)
            getResultPageModel()
        }
    }

    private fun getResultPageModel() {
        userAnswers.forEach() { answer ->
            if (correctAnswers.contains(answer)) {
                userScore++
            }
        }
        val scoreCoefficient: Float = userScore.toFloat() / correctAnswers.size.toFloat()
        when(scoreCoefficient) {
            in 0.0..0.5 -> userLevel = app.applicationContext.getString(R.string.bad_level)
            in 0.5..0.75 -> userLevel = app.applicationContext.getString(R.string.normal_level)
            in 0.75..1.0 -> userLevel = app.applicationContext.getString(R.string.high_level)
        }
        val resultPage = ResultPageModel(userScore, userLevel)
        resultPageModel.value = resultPage
    }
}