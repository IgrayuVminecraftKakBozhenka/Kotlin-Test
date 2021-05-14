package com.example.kotlintest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlintest.fragments.MainFragment
import com.example.kotlintest.fragments.MainFragment.OnBeginButtonPressed
import com.example.kotlintest.fragments.ResultFragment
import com.example.kotlintest.fragments.TestFragment
import com.example.kotlintest.fragments.TestFragment.OnTestFinished

class MainActivity : AppCompatActivity(), OnBeginButtonPressed, OnTestFinished {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val mainFragment = MainFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, mainFragment)
            .commit()
    }

    override fun onButtonPressed() {
        val testFragment = TestFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, testFragment)
            .commit()
    }

    override fun onTestFinished(userAnswers: ArrayList<String>) {
        val resultFragment = ResultFragment()
        val bundle = Bundle()
        bundle.putStringArrayList("result", userAnswers)
        resultFragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, resultFragment)
            .commit()
    }
}