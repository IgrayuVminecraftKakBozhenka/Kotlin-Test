package com.example.kotlintest.ui.common

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    abstract fun onBackPressed(): Boolean?
}