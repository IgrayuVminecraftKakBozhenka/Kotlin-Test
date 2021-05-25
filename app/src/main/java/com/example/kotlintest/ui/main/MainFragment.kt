package com.example.kotlintest.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.example.kotlintest.R
import com.example.kotlintest.fragmentMain
import com.example.kotlintest.ui.common.BaseFragment
import com.example.kotlintest.ui.dialog.Dialog

class MainFragment : BaseFragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
            .create(MainViewModel::class.java)
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

        toolbar.setTitle(R.string.main_menu)
        toolbar.setNavigationIcon(R.drawable.back_arrow)

        toolbar.setNavigationOnClickListener {
            showDialog()
        }

        beginButton.setOnClickListener {
            viewModel.goToTest()
        }
    }

    override fun onBackPressed(): Boolean {
        return false
    }

    private fun showDialog() {
        val dialog = Dialog(fragmentMain)
        val manager = requireActivity().supportFragmentManager
        dialog.show(manager, "dialog")
    }
}