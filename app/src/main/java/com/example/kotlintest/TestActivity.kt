package com.example.kotlintest


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.example.kotlintest.data.QuestionAndAnswerDatabase
import com.example.kotlintest.data.QuestionModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_test)
        
        temp()

        val db = QuestionAndAnswerDatabase.getDatabase(this)
        var questions: List<QuestionModel>
        val question = findViewById<TextView>(R.id.question_fragment_question)
        GlobalScope.launch {
            questions =  db.dao().readAllQuestionData()
            question.text = questions[0].question
        }


    }

    fun temp() {
        val button: Button
        button = findViewById(R.id.question_fragment_button_next)
        button.setOnClickListener() {
            Toast.makeText(this, "workaet", Toast.LENGTH_LONG).show()
        }
    }
}