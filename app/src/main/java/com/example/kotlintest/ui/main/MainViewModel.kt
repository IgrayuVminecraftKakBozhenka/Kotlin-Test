package com.example.kotlintest.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.kotlintest.App
import com.example.kotlintest.ui.common.Screens

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var app : Application = application

    fun goToTest() {
        (app as App).router.navigateTo(Screens.Regisration())
    }
}