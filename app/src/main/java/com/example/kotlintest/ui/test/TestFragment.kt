package com.example.kotlintest.ui.test

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.kotlintest.R
import com.example.kotlintest.data.models.AnswersModel
import com.example.kotlintest.data.QuestionAndAnswerDao
import com.example.kotlintest.data.QuestionAndAnswerDatabase
import com.example.kotlintest.data.models.QuestionModel
import com.example.kotlintest.fragmentTest
import com.example.kotlintest.ui.common.BaseFragment
import com.example.kotlintest.ui.dialog.Dialog
import com.example.kotlintest.view_model.PageViewModel
import kotlinx.coroutines.*

class TestFragment : BaseFragment() {

    interface OnTestFinished {
        fun onTestFinished(userAnswers: ArrayList<String>)
    }

    private lateinit var dao: QuestionAndAnswerDao
    private val questions: ArrayList<QuestionModel> = ArrayList()
    private val answers: ArrayList<AnswersModel> = ArrayList()
    private val userAnswers: ArrayList<String> = ArrayList()
    private val pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java)

    private var questionIndex = 0
    private var answerIndex = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dao = QuestionAndAnswerDatabase.getDatabase(context.applicationContext).dao()
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
        val previousButton = view.findViewById<Button>(R.id.question_fragment_button_previous)

        val radioGroup = view.findViewById<RadioGroup>(R.id.question_fragment_radio_button_group)
        val firstRadioButton =
            view.findViewById<RadioButton>(R.id.question_fragment_radio_first_answer)
        val secondRadioButton =
            view.findViewById<RadioButton>(R.id.question_fragment_radio_second_answer)
        val thirdRadioButton =
            view.findViewById<RadioButton>(R.id.question_fragment_radio_third_answer)
        val fourRadioButton =
            view.findViewById<RadioButton>(R.id.question_fragment_radio_four_answer)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitle(R.string.test)
        toolbar.setNavigationIcon(R.drawable.back_arrow)
        toolbar.setNavigationOnClickListener {
            val dialog = Dialog(fragmentTest)
            val manager = activity!!.supportFragmentManager
            dialog.show(manager, "dialog")
        }

        val pages = pageViewModel.getPageList()


        getQuestionFromDb(question)
        getAnswerFromDb(firstRadioButton, secondRadioButton, thirdRadioButton, fourRadioButton)



        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            view.findViewById<RadioButton>(checkedId)?.apply {
                answer = text.toString()
            }
        }

        nextButton.setOnClickListener {
            if (questionIndex < questions.size) {
                radioGroup.clearCheck()
                Log.d("index", questionIndex.toString())
                question.text = questions[questionIndex++].question
                firstRadioButton.text = answers[answerIndex++].answer
                secondRadioButton.text = answers[answerIndex++].answer
                thirdRadioButton.text = answers[answerIndex++].answer
                fourRadioButton.text = answers[answerIndex++].answer
                userAnswers.add(answer)
                Log.d("added", userAnswers.toString())
            } else {
                userAnswers.add(answer)
                val listener = activity as OnTestFinished?
                listener?.onTestFinished(userAnswers)
            }

        }

        previousButton.setOnClickListener {
            if (questionIndex >= 1 && questionIndex <= questions.size) {
                question.text = questions[questionIndex--].question
                Log.d("index", questionIndex.toString())
                userAnswers.remove(answer)
                radioGroup.clearCheck()
                fourRadioButton.text = answers[answerIndex--].answer
                thirdRadioButton.text = answers[answerIndex--].answer
                secondRadioButton.text = answers[answerIndex--].answer
                firstRadioButton.text = answers[answerIndex--].answer
            }
        }
    }

    override fun onBackPressed(): Boolean? {
        requireFragmentManager().beginTransaction()
            .remove(this)
            .commit()
        return true
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
                    question.text = questions[questionIndex++].question
                }
            }
        }
    }

    private fun getAnswerFromDb(
        firstRadioButton: RadioButton,
        secondRadioButton: RadioButton,
        thirdRadioButton: RadioButton,
        fourRadioButton: RadioButton
    ) {

        GlobalScope.launch(Dispatchers.IO) {
            val answersList = dao.readAllAnswerData()
            if (answersList.isNotEmpty()) {
                withContext(Dispatchers.Main) {
                    answers.addAll(answersList)
                    firstRadioButton.text = answers[answerIndex++].answer
                    secondRadioButton.text = answers[answerIndex++].answer
                    thirdRadioButton.text = answers[answerIndex++].answer
                    fourRadioButton.text = answers[answerIndex++].answer
                }
            }
        }
    }
}