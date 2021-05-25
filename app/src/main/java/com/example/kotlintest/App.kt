package com.example.kotlintest

import android.app.Application
import com.github.terrakok.cicerone.Cicerone


class App : Application() {
    private val cicerone = Cicerone.create()
    val router get() = cicerone.router
    val navigatorHolder get() = cicerone.getNavigatorHolder()

    override fun onCreate() {
        INSTANCE = this
        super.onCreate()
    }

    companion object {
        internal lateinit var INSTANCE: App
        private set
    }
}