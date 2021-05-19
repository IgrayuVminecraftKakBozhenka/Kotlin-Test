package com.example.kotlintest.ui.test

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotlintest.R
import com.example.kotlintest.data.QuestionAndAnswerDao
import com.example.kotlintest.data.QuestionAndAnswerDatabase
import com.example.kotlintest.data.models.PageModel
import com.example.kotlintest.fragmentTest
import com.example.kotlintest.ui.common.BaseFragment
import com.example.kotlintest.ui.dialog.Dialog
import com.example.kotlintest.view_model.PageViewModel

class TestFragment : BaseFragment() {

    interface OnTestFinished {
        fun onTestFinished(userAnswers: ArrayList<String>)
    }

    private lateinit var dao: QuestionAndAnswerDao
    private val userAnswers: ArrayList<String> = ArrayList()
    private lateinit var pageViewModel: PageViewModel

    private var pageIndex = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dao = QuestionAndAnswerDatabase.getDatabase(context.applicationContext).dao()
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java)
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

        var pageModels: ArrayList<PageModel>? = null

        pages.observe(viewLifecycleOwner, Observer {
            Log.d("data_from_model", pages.toString())
            pageModels = pages.value
            if (!pageModels.isNullOrEmpty()) {
                Log.d("data_from_model", pageModels?.size.toString())
                question.text = pageModels!![pageIndex].question
                firstRadioButton.text = pageModels!![pageIndex].answers[0]
                secondRadioButton.text = pageModels!![pageIndex].answers[1]
                thirdRadioButton.text = pageModels!![pageIndex].answers[2]
                fourRadioButton.text = pageModels!![pageIndex].answers[3]
                Log.d("index", pageIndex.toString())
            }
        })
        Log.d("data_from_model", pageModels.toString())

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            view.findViewById<RadioButton>(checkedId)?.apply {
                answer = text.toString()
            }
        }

        nextButton.setOnClickListener {
            if (pageIndex < pageModels!!.size - 1) {
                radioGroup.clearCheck()
                question.text = pageModels!![++pageIndex].question
                firstRadioButton.text = pageModels!![pageIndex].answers[0]
                secondRadioButton.text = pageModels!![pageIndex].answers[1]
                thirdRadioButton.text = pageModels!![pageIndex].answers[2]
                fourRadioButton.text = pageModels!![pageIndex].answers[3]
                userAnswers.add(answer)
                Log.d("index", pageIndex.toString())
                Log.d("added", userAnswers.toString())
            } else {
                userAnswers.add(answer)
                val listener = activity as OnTestFinished?
                listener?.onTestFinished(userAnswers)
            }

        }

        previousButton.setOnClickListener {
            if (pageIndex >= 1 && pageIndex <= pageModels!!.size) {
                question.text = pageModels!![--pageIndex].question
                Log.d("index", pageIndex.toString())
                userAnswers.remove(answer)
                radioGroup.clearCheck()
                firstRadioButton.text = pageModels!![pageIndex].answers[0]
                secondRadioButton.text = pageModels!![pageIndex].answers[1]
                thirdRadioButton.text = pageModels!![pageIndex].answers[2]
                fourRadioButton.text = pageModels!![pageIndex].answers[3]
            }
        }
    }

    override fun onBackPressed(): Boolean? {
        parentFragmentManager.beginTransaction()
            .remove(this)
            .commit()
        return true
    }
}
