package com.example.kotlintest.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlintest.R
import com.example.kotlintest.ui.common.BaseFragment
import com.example.kotlintest.ui.main.MainFragment
import com.example.kotlintest.ui.main.MainFragment.OnBeginButtonPressed
import com.example.kotlintest.ui.result.ResultFragment
import com.example.kotlintest.ui.result.ResultFragment.GoToMain
import com.example.kotlintest.ui.test.TestFragment
import com.example.kotlintest.ui.test.TestFragment.OnTestFinished

class MainActivity : AppCompatActivity(), OnBeginButtonPressed, OnTestFinished, GoToMain {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val mainFragment = MainFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, mainFragment)
            .commit()
    }

    override fun onBackPressed() {
        val fragments = supportFragmentManager.fragments
        val fragmentsLength = fragments.size - 1
        for (i in fragmentsLength downTo 0) {
            val fragment = fragments[i]
            if (fragment is BaseFragment) {
                val backPressedResult = fragment.onBackPressed() ?: return
            }
        }
        super.onBackPressed()
    }

    override fun onButtonPressed() {
        val testFragment = TestFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, testFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onTestFinished(userAnswers: ArrayList<String>) {
        val resultFragment = ResultFragment()
        val bundle = Bundle()
        bundle.putStringArrayList("result", userAnswers)
        resultFragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, resultFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun goToMain() {
        val mainFragment = MainFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, mainFragment)
            .commit()
    }
}