package com.example.kotlintest.ui.result

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class ResultViewModel(application: Application) : AndroidViewModel(application){

    private val correctAnswers = ArrayList<String>()
    private val userAnswers = ArrayList<String>()

    init {


    }
}