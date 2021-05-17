package com.example.kotlintest.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toolbar
import com.example.kotlintest.R
import com.example.kotlintest.ui.common.BaseFragment

class MainFragment: BaseFragment() {

    interface OnBeginButtonPressed {
        fun onButtonPressed()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val beginButton = view.findViewById<Button>(R.id.begin_button)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)

        toolbar.title = "Main menu"
        toolbar.setNavigationIcon(R.drawable.back_arrow)

        beginButton.setOnClickListener {
            val listener = activity as OnBeginButtonPressed?
            listener?.onButtonPressed()
        }

    }

    override fun onBackPressed(): Boolean {
        return false
    }
}