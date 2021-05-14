package com.example.kotlintest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.kotlintest.R

class ResultFragment: Fragment() {

    private var userAnswers = ArrayList<String>()

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

        userAnswers = arguments?.getStringArrayList("result")!!
    }
}