package com.example.kotlintest

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.kotlintest.data.AnswersModel
import com.example.kotlintest.data.QuestionAndAnswerDao
import com.example.kotlintest.data.QuestionAndAnswerDatabase
import com.example.kotlintest.data.QuestionModel
import kotlinx.coroutines.*

class TestFragment() : Fragment() {

    lateinit var mContext: Context
    private var dao: QuestionAndAnswerDao? = null
    private var questions: ArrayList<QuestionModel> = ArrayList()
    private var answers: ArrayList<AnswersModel> = ArrayList()

    private var questionIndex = 0
    private var answerIndex = 0

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

        val radioGroup = view.findViewById<RadioGroup>(R.id.question_fragment_radio_button_group)
        val firstRadioButton = view.findViewById<RadioButton>(R.id.question_fragment_radio_first_answer)
        val secondRadioButton = view.findViewById<RadioButton>(R.id.question_fragment_radio_second_answer)
        val thirdRadioButton = view.findViewById<RadioButton>(R.id.question_fragment_radio_third_answer)
        val fourRadioButton = view.findViewById<RadioButton>(R.id.question_fragment_radio_four_answer)

        //radioGroup.setOnCheckedChangeListener() {
        //
        //}


        getQuestionFromDb(question)
        getAnswerFromDb(firstRadioButton, secondRadioButton, thirdRadioButton, fourRadioButton)

        nextButton.setOnClickListener {
            if (questionIndex < questions.size) {
                question.text = questions[questionIndex++].question
                firstRadioButton.text = answers[answerIndex++].answer
                secondRadioButton.text = answers[answerIndex++].answer
                thirdRadioButton.text = answers[answerIndex++].answer
                fourRadioButton.text = answers[answerIndex++].answer
            } else
             Toast.makeText(mContext, "все", Toast.LENGTH_SHORT).show()
        }

    }

    private fun getQuestionFromDb(question: TextView) {
        GlobalScope.launch {
            val questionsFromDb = async(Dispatchers.IO) {
                dao?.readAllQuestionData()
            }
            withContext(Dispatchers.Main) {
                val questionsResult = questionsFromDb.await()
                if (!questionsResult.isNullOrEmpty()) {
                    questions.addAll(questionsResult)
                    question.text = questions[questionIndex].question
                    questionIndex++
                }
            }
        }
    }

    private fun getAnswerFromDb(firstRadioButton: RadioButton, secondRadioButton: RadioButton,
                                thirdRadioButton: RadioButton, fourRadioButton: RadioButton) {
        GlobalScope.launch {

            val answersFromDb = async(Dispatchers.IO) {
                dao?.readAllAnswerData()
            }
            withContext(Dispatchers.Main) {
                val answersResult = answersFromDb.await()
                if (!answersResult.isNullOrEmpty()) {
                    answers.addAll(answersResult)
                    firstRadioButton.text = answers[answerIndex++].answer
                    secondRadioButton.text = answers[answerIndex++].answer
                    thirdRadioButton.text = answers[answerIndex++].answer
                    fourRadioButton.text = answers[answerIndex++].answer
                }
            }

        }
    }
}