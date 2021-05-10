package com.example.kotlintest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.kotlintest.data.QuestionAndAnswerDatabase
import com.example.kotlintest.data.QuestionAndAnswerViewModel
import com.example.kotlintest.data.QuestionModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = QuestionAndAnswerDatabase.getDatabase(this)
        val question = QuestionModel(0, "Да?")
        val answer =
        GlobalScope.launch {
            db.dao().addQuestion(question)
        }

        val beginButton = findViewById<Button>(R.id.begin_button)
        beginButton.setOnClickListener {
            val intent = Intent(this, TestActivity::class.java)
            startActivity(intent)


        }


    }
}