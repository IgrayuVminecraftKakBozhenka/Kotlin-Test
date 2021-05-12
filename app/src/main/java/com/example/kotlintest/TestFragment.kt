package com.example.kotlintest

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.kotlintest.data.QuestionAndAnswerDao
import com.example.kotlintest.data.QuestionAndAnswerDatabase
import com.example.kotlintest.data.QuestionModel
import kotlinx.coroutines.*

class TestFragment() : Fragment() {

    lateinit var mContext: Context
    private var dao: QuestionAndAnswerDao? = null
    private var questions: ArrayList<QuestionModel> = ArrayList()
    private var questionIndex = 0

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

        dao =QuestionAndAnswerDatabase.getDatabase(mContext).dao()
        val question = view.findViewById<TextView>(R.id.question_fragment_question)
        val nextButton = view.findViewById<Button>(R.id.question_fragment_button_next)

        getQuestionFromDb(question)

        nextButton.setOnClickListener {
            if (questionIndex < questions.size) {
                question.text = questions[questionIndex++].question
            } else
             Toast.makeText(mContext, "все", Toast.LENGTH_SHORT).show()
        }

    }

    private fun getQuestionFromDb(question: TextView) {
        GlobalScope.launch {
            val data = async(Dispatchers.IO) {
                dao?.readAllQuestionData()
            }
            withContext(Dispatchers.Main) {
                val result = data.await()
                if (!result.isNullOrEmpty()) {
                    questions.addAll(result)
                    question.text = questions[questionIndex].question
                    questionIndex++
                }
            }
        }
    }
}