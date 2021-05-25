package com.example.kotlintest.ui.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.kotlintest.R
import com.example.kotlintest.fragmentTest
import com.example.kotlintest.ui.common.BaseFragment
import com.example.kotlintest.ui.dialog.Dialog

class TestFragment : BaseFragment() {

    private lateinit var viewModel: TestViewModel

    private lateinit var toolbar: Toolbar

    private lateinit var question: TextView
    private lateinit var nextButton: Button
    private lateinit var previousButton: Button
    private lateinit var radioGroup: RadioGroup
    private lateinit var firstRadioButton: RadioButton
    private lateinit var secondRadioButton: RadioButton
    private lateinit var thirdRadioButton: RadioButton
    private lateinit var fourRadioButton: RadioButton


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

        initViews()

        toolbar.setNavigationOnClickListener {
            showDialog()
        }

        viewModel.page.observe(viewLifecycleOwner, { page ->
            question.text = page.question
            firstRadioButton.text = page.answers[0]
            secondRadioButton.text = page.answers[1]
            thirdRadioButton.text = page.answers[2]
            fourRadioButton.text = page.answers[3]
        })

        nextButton.setOnClickListener {
            radioGroup.clearCheck()
            viewModel.getNextPage(answer)
        }

        previousButton.setOnClickListener {
            radioGroup.clearCheck()
            viewModel.getPreviousPage()
        }

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            view.findViewById<RadioButton>(checkedId)?.apply {
                answer = text.toString()
            }
        }

    }

    override fun onBackPressed(): Boolean {
        parentFragmentManager.beginTransaction()
            .remove(this)
            .commit()
        return true
    }


    private fun showDialog() {
        val dialog = Dialog(fragmentTest)
        val manager = requireActivity().supportFragmentManager
        dialog.show(manager, "dialog")
    }

    private fun initViews() {
        question = requireView().findViewById(R.id.question_fragment_question)
        nextButton = requireView().findViewById(R.id.question_fragment_button_next)
        previousButton = requireView().findViewById(R.id.question_fragment_button_previous)

        radioGroup = requireView().findViewById(R.id.question_fragment_radio_button_group)
        firstRadioButton =
            requireView().findViewById(R.id.question_fragment_radio_first_answer)
        secondRadioButton =
            requireView().findViewById(R.id.question_fragment_radio_second_answer)
        thirdRadioButton =
            requireView().findViewById(R.id.question_fragment_radio_third_answer)
        fourRadioButton =
            requireView().findViewById(R.id.question_fragment_radio_four_answer)

        toolbar = requireView().findViewById(R.id.toolbar)
        toolbar.setTitle(R.string.test)
        toolbar.setNavigationIcon(R.drawable.back_arrow)

    }
}
