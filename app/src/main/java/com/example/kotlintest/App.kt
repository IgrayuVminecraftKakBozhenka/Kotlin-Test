package com.example.kotlintest

import android.app.Application
import ru.terrakok.cicerone.Cicerone


class App : Application() {
    private val cicerone = Cicerone.create()
    val router get() = cicerone.router
    val navigatorHolder get() = cicerone.navigatorHolder

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {
        internal lateinit var INSTANCE: App
        private set
    }

}