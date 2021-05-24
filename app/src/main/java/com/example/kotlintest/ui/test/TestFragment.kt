package com.example.kotlintest.ui.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotlintest.R
import com.example.kotlintest.data.models.PageModel
import com.example.kotlintest.fragmentTest
import com.example.kotlintest.ui.common.BaseFragment
import com.example.kotlintest.ui.dialog.Dialog
import java.util.*
import kotlin.collections.ArrayList

class TestFragment : BaseFragment() {

    interface OnTestFinished {
        fun onTestFinished(userAnswers: ArrayList<String>)
    }

    private lateinit var viewModel: TestViewModel

    private val userAnswers: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
            .create(TestViewModel::class.java)
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
            showDialog()
        }

        viewModel.page.observe(viewLifecycleOwner, Observer { page ->
            question.text = page.question
            firstRadioButton.text = page.answers[0]
            secondRadioButton.text = page.answers[1]
            thirdRadioButton.text = page.answers[2]
            fourRadioButton.text = page.answers[3]
        })

        nextButton.setOnClickListener {
            nextButtonClick(radioGroup, answer, question, firstRadioButton, secondRadioButton,
                thirdRadioButton, fourRadioButton)
        }

        previousButton.setOnClickListener {
            previousButtonClick(radioGroup, answer, question, firstRadioButton, secondRadioButton,
                thirdRadioButton, fourRadioButton)
        }

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            view.findViewById<RadioButton>(checkedId)?.apply {
                answer = text.toString()
            }
        }
    }

    override fun onBackPressed(): Boolean? {
        parentFragmentManager.beginTransaction()
            .remove(this)
            .commit()
        return true
    }

    private fun nextButtonClick(radioGroup: RadioGroup, answer: String, question: TextView,
                        firstRadioButton: RadioButton, secondRadioButton: RadioButton,
                        thirdRadioButton: RadioButton, fourRadioButton: RadioButton) {
        radioGroup.clearCheck()
        val page = viewModel.getNextPage()?.value
        if (page != null) {
            userAnswers.add(answer)
            question.text = page.question
            firstRadioButton.text = page.answers[0]
            secondRadioButton.text = page.answers[1]
            thirdRadioButton.text = page.answers[2]
            fourRadioButton.text = page.answers[3]
        }
    }

    private fun previousButtonClick(radioGroup: RadioGroup, answer: String, question: TextView,
                            firstRadioButton: RadioButton, secondRadioButton: RadioButton,
                            thirdRadioButton: RadioButton, fourRadioButton: RadioButton) {
        radioGroup.clearCheck()
        val page = viewModel.getPreviousPage()?.value
        if (page != null) {
            userAnswers.remove(answer)
            question.text = page.question
            firstRadioButton.text = page.answers[0]
            secondRadioButton.text = page.answers[1]
            thirdRadioButton.text = page.answers[2]
            fourRadioButton.text = page.answers[3]
        } else {
            Toast.makeText(context, R.string.this_is_a_first_question, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDialog() {
        val dialog = Dialog(fragmentTest)
        val manager = activity!!.supportFragmentManager
        dialog.show(manager, "dialog")
    }
}
