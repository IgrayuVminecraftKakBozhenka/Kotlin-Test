package com.example.kotlintest


import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlintest.data.QuestionAndAnswerDatabase
import com.example.kotlintest.data.QuestionModel

//import kotlinx.coroutines.GlobalScope
//import kotlinx.coroutines.launch


class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_test)

        val testFragment = TestFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, testFragment)
            .commit()
    }
}