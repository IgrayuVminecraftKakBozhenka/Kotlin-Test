package com.example.kotlintest

import android.content.Context
import android.os.Bundle
import android.util.Log
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

class TestFragment : Fragment() {

    private lateinit var dao: QuestionAndAnswerDao
    private val questions: ArrayList<QuestionModel> = ArrayList()
    private val answers: ArrayList<AnswersModel> = ArrayList()
    private val userAnswers: ArrayList<String> = ArrayList()

    private var questionIndex = 0
    private var answerIndex = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dao = QuestionAndAnswerDatabase.getDatabase(requireContext().applicationContext).dao()
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

        var answer = ""

        val question = view.findViewById<TextView>(R.id.question_fragment_question)
        val nextButton = view.findViewById<Button>(R.id.question_fragment_button_next)

        val radioGroup = view.findViewById<RadioGroup>(R.id.question_fragment_radio_button_group)
        val firstRadioButton =
            view.findViewById<RadioButton>(R.id.question_fragment_radio_first_answer)
        val secondRadioButton =
            view.findViewById<RadioButton>(R.id.question_fragment_radio_second_answer)
        val thirdRadioButton =
            view.findViewById<RadioButton>(R.id.question_fragment_radio_third_answer)
        val fourRadioButton =
            view.findViewById<RadioButton>(R.id.question_fragment_radio_four_answer)


        getQuestionFromDb(question)
        getAnswerFromDb(firstRadioButton, secondRadioButton, thirdRadioButton, fourRadioButton)

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            view.findViewById<RadioButton>(checkedId)?.apply {
                answer = text as String
            }
        }

        nextButton.setOnClickListener {
            if (questionIndex < questions.size) {
                radioGroup.clearCheck()
                question.text = questions[questionIndex++].question
                firstRadioButton.text = answers[answerIndex++].answer
                secondRadioButton.text = answers[answerIndex++].answer
                thirdRadioButton.text = answers[answerIndex++].answer
                fourRadioButton.text = answers[answerIndex++].answer
                saveAnswer(answer)
                Log.d("answer added", userAnswers.toString())
            } else
                Toast.makeText(requireContext().applicationContext, "все", Toast.LENGTH_SHORT)
                    .show()
        }
    }

    private fun getQuestionFromDb(question: TextView) {
        GlobalScope.launch {
            val questionsFromDb = async(Dispatchers.IO) {
                dao.readAllQuestionData()
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

    private fun getAnswerFromDb(
        firstRadioButton: RadioButton,
        secondRadioButton: RadioButton,
        thirdRadioButton: RadioButton,
        fourRadioButton: RadioButton)
    {
        GlobalScope.launch {
            val answersFromDb = async(Dispatchers.IO) {
                dao.readAllAnswerData()
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

    private fun saveAnswer(answer: String) {
        userAnswers.add(answer)
    }
}