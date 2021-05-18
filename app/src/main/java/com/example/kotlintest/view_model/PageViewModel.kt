package com.example.kotlintest.view_model

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlintest.data.QuestionAndAnswerDatabase
import com.example.kotlintest.data.models.PageModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PageViewModel : AndroidViewModel() {
    val pageList : MutableLiveData<List<PageModel>> = MutableLiveData()
    private val dao = QuestionAndAnswerDatabase.getDatabase(getApplication()).dao()
    init {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            val questions = dao.readAllQuestionData()
            val answers = dao.readAllAnswerData()
            questions.for
        }
    }
}