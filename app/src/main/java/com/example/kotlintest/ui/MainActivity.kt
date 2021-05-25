package com.example.kotlintest.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlintest.App
import com.example.kotlintest.R
import com.example.kotlintest.ui.common.BaseFragment
import com.example.kotlintest.ui.common.Screens
import com.github.terrakok.cicerone.androidx.AppNavigator

class MainActivity : AppCompatActivity() {

    private val navigator = AppNavigator(this, R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        (application as App).router.newRootScreen(Screens.Main())
        Log.d("debug", "${application is App}")
    }

    override fun onBackPressed() {
        val fragments = supportFragmentManager.fragments
        val fragmentsLength = fragments.size - 1
        for (i in fragmentsLength downTo 0) {
            val fragment = fragments[i]
            if (fragment is BaseFragment) {
                val backPressedResult = fragment.onBackPressed() ?: return
            }
        }
        super.onBackPressed()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        (application as App).navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        (application as App).navigatorHolder.removeNavigator()
    }


//    override fun onTestFinished(userAnswers: ArrayList<String>) {
//        val resultFragment = ResultFragment()
//        val bundle = Bundle()
//        bundle.putStringArrayList("result", userAnswers)
//        resultFragment.arguments = bundle
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.container, resultFragment)
//            .addToBackStack(null)
//            .commit()
//    }

//    override fun goToMain() {
//        val mainFragment = MainFragment()
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.container, mainFragment)
//            .commit()
//    }

}