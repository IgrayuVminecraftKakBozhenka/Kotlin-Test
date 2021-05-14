package com.example.kotlintest.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.kotlintest.R
import com.example.kotlintest.data.QuestionAndAnswerDao
import com.example.kotlintest.data.QuestionAndAnswerDatabase
import kotlinx.coroutines.*

class ResultFragment: Fragment() {

    private lateinit var dao: QuestionAndAnswerDao
    private var userAnswers = ArrayList<String>()
    private val correctAnswers = ArrayList<String>()

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

        getCorrectAnswerFromDb()
        userAnswers = arguments?.getStringArrayList("result")!!

        val score = getScore()

        userScore.text = score.toString()

        when (score) {
            in 0..2 -> userLevel.text = R.string.bad_level.toString()
            in 2..4 -> userLevel.text = R.string.normal_level.toString()
            5 -> userLevel.text = R.string.high_level.toString()
        }

    }

    private fun getCorrectAnswerFromDb() {
        GlobalScope.launch {
            val correctAnswersFromDb = async(Dispatchers.IO) {
                dao.getCorrectAnswer()
            }
            withContext(Dispatchers.Main) {
                val answersResult = correctAnswersFromDb.await()
                if (!answersResult.isNullOrEmpty()) {
                    correctAnswers.addAll(answersResult)
                }
            }
        }
    }

    private fun getScore(): Int {
        var score = 0
        userAnswers.forEach {
            if (correctAnswers.contains(it))
                score++
        }
        return score
    }


}