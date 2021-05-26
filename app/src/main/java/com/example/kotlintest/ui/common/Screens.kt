package com.example.kotlintest.ui.common
import com.example.kotlintest.ui.main.MainFragment
import com.example.kotlintest.ui.result.ResultFragment
import com.example.kotlintest.ui.test.TestFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {

    fun Main() = FragmentScreen {
        MainFragment()
    }

    fun Test() = FragmentScreen {
        TestFragment()
    }

    fun Result(userAnswer: ArrayList<String>) = FragmentScreen {
        ResultFragment.newInstance(userAnswer)
    }
}