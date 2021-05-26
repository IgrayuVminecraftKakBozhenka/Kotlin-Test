package com.example.kotlintest.ui.test

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kotlintest.App
import com.example.kotlintest.data.QuestionAndAnswerDatabase
import com.example.kotlintest.data.models.PageModel
import com.example.kotlintest.ui.common.Screens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TestViewModel(application: Application) : AndroidViewModel(application) {

    private val pages = mutableListOf<PageModel>()
    val page: MutableLiveData<PageModel> = MutableLiveData()
    private val dao = QuestionAndAnswerDatabase.getDatabase(getApplication()).dao()
    private val userAnswers = ArrayList<String>()

    private val app = application

    var pageIndex = 0
        private set

    init {
        viewModelScope.launch {
            val questions = withContext(Dispatchers.IO) { dao.readAllQuestionData() }
            val answers = withContext(Dispatchers.IO) { dao.readAllAnswerData() }
            val pageModels: ArrayList<PageModel> = ArrayList()
            withContext(Dispatchers.Default) {
                questions.forEach {
                    val question = it.question
                    val questionId = it._id
                    val answersToPage: ArrayList<String> = ArrayList()
                    answers.forEach {
                        if (it.question_id == questionId)
                            answersToPage.add(it.answer)
                    }
                    val pageModel = PageModel(question, answersToPage)
                    pageModels.add(pageModel)

                }
            }
            pages.addAll(pageModels)
            page.value = pages[pageIndex]
            Log.d("pages", page.value.toString())
        }
    }

    fun getNextPage(answer: String) {
        if (pageIndex < pages.size - 1) {
            userAnswers.add(answer)
            page.value = pages[++pageIndex]
        } else {
            userAnswers.add(answer)
            goToResultFragment()
        }
    }

    fun getPreviousPage() {
        if (pageIndex > 0) {
            userAnswers.removeAt(userAnswers.size - 1)
            page.value = pages[--pageIndex]
        }
    }

    fun exit() {
        TODO("Not yet implemented")
    }

    private fun goToResultFragment() {
        (app as App).router.navigateTo(Screens.Result(userAnswers))
    }


}