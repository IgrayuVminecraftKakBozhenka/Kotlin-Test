package com.example.kotlintest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlintest.MainFragment.OnBeginButtonPressed

class MainActivity : AppCompatActivity(), OnBeginButtonPressed {



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
}