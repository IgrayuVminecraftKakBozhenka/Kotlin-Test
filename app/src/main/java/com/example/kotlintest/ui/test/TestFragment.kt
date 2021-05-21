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

class TestFragment : BaseFragment() {

    interface OnTestFinished {
        fun onTestFinished(userAnswers: ArrayList<String>)
    }

    private lateinit var viewModel: TestViewModel

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
            val dialog = Dialog(fragmentTest)
            val manager = activity!!.supportFragmentManager
            dialog.show(manager, "dialog")
        }


        //question.text = viewModel.getNextPage().question

        viewModel.page.observe(viewLifecycleOwner, Observer { page ->
            question.text = page.question
            firstRadioButton.text = page.answers[0]
            secondRadioButton.text = page.answers[1]
            thirdRadioButton.text = page.answers[2]
            fourRadioButton.text = page.answers[3]
        })

        nextButton.setOnClickListener {
            val page: PageModel = viewModel.getNextPage().value!!
            question.text = page.question
            firstRadioButton.text = page.answers[0]
            secondRadioButton.text = page.answers[1]
            thirdRadioButton.text = page.answers[2]
            fourRadioButton.text = page.answers[3]
        }


        //pages.observe(viewLifecycleOwner, Observer { pageModels ->
        //    Log.d("data_from_model", pages.toString())
        //    if (pageModels.isNotEmpty()) {
        //        Log.d("data_from_model", pageModels?.size.toString())
        //        question.text = pageModels!![pageIndex].question
        //        firstRadioButton.text = pageModels[pageIndex].answers[0]
        //        secondRadioButton.text = pageModels[pageIndex].answers[1]
        //        thirdRadioButton.text = pageModels[pageIndex].answers[2]
        //        fourRadioButton.text = pageModels[pageIndex].answers[3]
        //        Log.d("index", pageIndex.toString())
        //    }
        //})

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            view.findViewById<RadioButton>(checkedId)?.apply {
                answer = text.toString()
            }
        }


//        nextButton.setOnClickListener {
//            val pageModels = viewModel.pagesList.value
//            if (pageIndex < pageModels!!.size - 1) {
//                radioGroup.clearCheck()
//                question.text = pageModels[++pageIndex].question
//                firstRadioButton.text = pageModels[pageIndex].answers[0]
//                secondRadioButton.text = pageModels[pageIndex].answers[1]
//                thirdRadioButton.text = pageModels[pageIndex].answers[2]
//                fourRadioButton.text = pageModels[pageIndex].answers[3]
//                userAnswers.add(answer)
//                Log.d("index", pageIndex.toString())
//                Log.d("added", userAnswers.toString())
//            } else {
//                userAnswers.add(answer)
//                val listener = activity as OnTestFinished?
//                listener?.onTestFinished(userAnswers)
//            }
//
//        }
//
//        previousButton.setOnClickListener {
//            val pageModels = viewModel.pagesList.value
//            if (pageIndex >= 1 && pageIndex <= pageModels!!.size) {
//                question.text = pageModels[--pageIndex].question
//                Log.d("index", pageIndex.toString())
//                userAnswers.remove(answer)
//                radioGroup.clearCheck()
//                firstRadioButton.text = pageModels[pageIndex].answers[0]
//                secondRadioButton.text = pageModels[pageIndex].answers[1]
//                thirdRadioButton.text = pageModels[pageIndex].answers[2]
//                fourRadioButton.text = pageModels[pageIndex].answers[3]
//            }
//        }
    }

    override fun onBackPressed(): Boolean? {
        viewModel.exit()
        requireFragmentManager().beginTransaction()
            .remove(this)
            .commit()
        return true
    }
}
