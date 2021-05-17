package com.example.kotlintest.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.kotlintest.R

class Dialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(R.string.are_you_sure_want_to_exit)
                .setCancelable(true)
                .setPositiveButton(R.string.no) { _, _ ->
                }
                .setNegativeButton(
                    R.string.yes
                ) { _, _ ->
                    activity!!.onBackPressed()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}