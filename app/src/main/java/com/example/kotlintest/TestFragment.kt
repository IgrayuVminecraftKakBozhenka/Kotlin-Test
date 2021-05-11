package com.example.kotlintest

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.kotlintest.data.QuestionAndAnswerDatabase
import com.example.kotlintest.data.QuestionModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class TestFragment() : Fragment() {

    lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val question = view.findViewById<TextView>(R.id.question_fragment_question)
        var questions: List<QuestionModel>
        GlobalScope.launch {
            delay(2000)
            val data = async(Dispatchers.IO) {
                QuestionAndAnswerDatabase.getDatabase(mContext).dao().readAllQuestionData()
            }
            withContext(Dispatchers.Main) {
                questions = data.await()
                question.text = questions[0].question
            }
        }
        //val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
        //question.text = questions[0].question

    }
}