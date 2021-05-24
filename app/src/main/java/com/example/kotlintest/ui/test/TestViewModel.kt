package com.example.kotlintest.ui.test

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.kotlintest.data.QuestionAndAnswerDatabase
import com.example.kotlintest.data.models.PageModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TestViewModel(application: Application) : AndroidViewModel(application) {

    private val pages = mutableListOf<PageModel>()
    val page : MutableLiveData<PageModel> = MutableLiveData()
    private val dao = QuestionAndAnswerDatabase.getDatabase(getApplication()).dao()

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

    fun getNextPage(): MutableLiveData<PageModel>? {
        if (pageIndex < pages.size - 1) {
            page.value = pages[++pageIndex]
            return page
        }
        return null
    }

    fun getPreviousPage(): MutableLiveData<PageModel>? {
        if (pageIndex > 0) {
            page.value = pages[--pageIndex]
            return page
        }
        return null
    }


    fun exit() {
        TODO("Not yet implemented")
    }
}