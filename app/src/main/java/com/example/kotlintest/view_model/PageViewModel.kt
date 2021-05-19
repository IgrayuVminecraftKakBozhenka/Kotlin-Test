package com.example.kotlintest.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.kotlintest.data.QuestionAndAnswerDatabase
import com.example.kotlintest.data.models.AnswersModel
import com.example.kotlintest.data.models.PageModel
import kotlinx.coroutines.*

class PageViewModel(application: Application) : AndroidViewModel(application) {
    private val pageList: MutableLiveData<ArrayList<PageModel>> = MutableLiveData()
    private val dao = QuestionAndAnswerDatabase.getDatabase(getApplication()).dao()

    init {
        val scope = CoroutineScope(Job() + Dispatchers.IO)
        val job = scope.launch {
            val questions = dao.readAllQuestionData()
            val answers = dao.readAllAnswerData()
            val pageModels: ArrayList<PageModel> = ArrayList()
            questions.forEach {
                val question = it.question
                val questionId = it._id
                val answersToPage: ArrayList<AnswersModel> = ArrayList()
                answers.forEach {
                    if (it.question_id == questionId)
                        answersToPage.add(it)
                }
                val pageModel = PageModel(question, answersToPage)
                pageModels.add(pageModel)
            }
            withContext(Dispatchers.Main) {
                pageList.value = pageModels
                Log.d("data_from_list", pageList.toString())
            }
        }
    }

    fun getPageList() = pageList
}