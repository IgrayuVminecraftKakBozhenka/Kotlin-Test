package com.example.kotlintest

import android.app.Application
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router

class App : Application() {
    private lateinit var cicerone: Cicerone<Router>

    override fun onCreate() {
        super.onCreate()
        cicerone = Cicerone.create()
    }

    fun getNavigatorHolder() : NavigatorHolder = cicerone.navigatorHolder

    fun getRouter(): Router = cicerone.router
}