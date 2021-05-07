package com.example.kotlintest.data

class QuestionModel(_question: String, _correctAnswerId: Int, _answerArray: Array<String>) {
    val question = _question
    val correctAnswerId = _correctAnswerId
    val answerArray = _answerArray
}