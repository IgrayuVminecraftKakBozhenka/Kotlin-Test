package com.example.kotlintest.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.example.kotlintest.R
import com.example.kotlintest.ui.common.BaseFragment
import com.example.kotlintest.ui.common.USER_ANSWERS
import com.example.kotlintest.ui.common.fragmentResult
import com.example.kotlintest.ui.dialog.Dialog
import java.util.*

class ResultFragment : BaseFragment() {

    private lateinit var viewModel: ResultViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userAnswers = arguments?.getStringArrayList(USER_ANSWERS)
        viewModel = ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
            .create(ResultViewModel::class.java)
        viewModel.setUserAnswers(userAnswers!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userLevel = view.findViewById<TextView>(R.id.result_fragment_level_text_view)
        val userScore = view.findViewById<TextView>(R.id.result_fragment_score_text_view)
        val toStartButton = view.findViewById<Button>(R.id.result_fragment_button_to_main)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitle(R.string.test)
        toolbar.setNavigationIcon(R.drawable.back_arrow)
        toolbar.setNavigationOnClickListener {
            showDialog()
        }

        viewModel.resultPageModel.observe(viewLifecycleOwner, { resultPage ->
            userLevel.text = resultPage.level
            userScore.text = resultPage.score.toString()
        })


    }


    override fun onBackPressed(): Boolean? {
        parentFragmentManager.beginTransaction()
            .remove(this)
            .commit()
        return true
    }

    private fun showDialog() {
        val dialog = Dialog(fragmentResult)
        val manager = requireActivity().supportFragmentManager
        dialog.show(manager, "dialog")
    }

    companion object {

        fun newInstance(userAnswers: ArrayList<String>): ResultFragment =
            ResultFragment().apply {
                arguments = Bundle().apply {
                    putStringArrayList(USER_ANSWERS, userAnswers)
                }
            }
    }

}
