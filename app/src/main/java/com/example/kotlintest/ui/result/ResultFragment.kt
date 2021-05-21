package com.example.kotlintest.ui.result

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toolbar
import com.example.kotlintest.R
import com.example.kotlintest.data.QuestionAndAnswerDao
import com.example.kotlintest.data.QuestionAndAnswerDatabase
import com.example.kotlintest.fragmentResult
import com.example.kotlintest.ui.common.BaseFragment
import com.example.kotlintest.ui.dialog.Dialog
import kotlinx.coroutines.*
import java.util.*

class ResultFragment : BaseFragment() {

    interface GoToMain {
        fun goToMain()
    }

    private lateinit var dao: QuestionAndAnswerDao
    private var userAnswers = ArrayList<String>()
    private val correctAnswers = ArrayList<String>()
    private var score = 0


    override fun onAttach(context: Context) {
        super.onAttach(context)
        dao = QuestionAndAnswerDatabase.getDatabase(requireContext().applicationContext).dao()
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
            val dialog = Dialog(fragmentResult)
            val manager = activity!!.supportFragmentManager
            dialog.show(manager, "dialog")
        }

        userAnswers = arguments?.getStringArrayList("result")!!
        getCorrectAnswerFromDb(userScore, userLevel)

        toStartButton.setOnClickListener {
            val listener = activity as GoToMain?
            listener?.goToMain()
        }

    }

    private fun getCorrectAnswerFromDb(userScore: TextView, userLevel: TextView) {
        GlobalScope.launch {
            val correctAnswersFromDb = async(Dispatchers.IO) {
                dao.getCorrectAnswer()
            }
            withContext(Dispatchers.Main) {
                val answersResult = correctAnswersFromDb.await()
                if (!answersResult.isNullOrEmpty()) {
                    correctAnswers.addAll(answersResult)
                    getScore()
                    userScore.text = score.toString()
                    when (score) {
                        in 0..2 -> userLevel.setText(R.string.bad_level)
                        in 2..4 -> userLevel.setText(R.string.normal_level)
                        5 -> userLevel.setText(R.string.high_level)
                    }
                }
            }
        }
    }

    private fun getScore() {
        Log.d("correct", userAnswers.toString())
        userAnswers.forEach {
            if (correctAnswers.contains(it))
                score++
        }
    }

    override fun onBackPressed(): Boolean? {
        requireFragmentManager().beginTransaction()
            .remove(this)
            .commit()
        return true
    }

}
