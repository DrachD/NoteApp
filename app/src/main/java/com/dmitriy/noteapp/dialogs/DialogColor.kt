package com.dmitriy.noteapp.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color.TRANSPARENT
import com.dmitriy.noteapp.model.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.dmitriy.noteapp.MainActivity
import com.dmitriy.noteapp.R
import com.dmitriy.noteapp.databinding.DialogUpdateColorBinding
import com.dmitriy.noteapp.fragments.AddNoteFragment
import com.dmitriy.noteapp.utils.ColorCallbacks
import com.dmitriy.noteapp.viewmodel.ColorListViewModel

class DialogColor(
    private val colorCallbacks: ColorCallbacks
) : View.OnClickListener {

    private var alertDialog: AlertDialog? = null
    private var lLayoutList = hashMapOf<Int, LinearLayout>()

    fun showDialog(act: MainActivity?, view: View, list: HashMap<Int, Color>)
    {
        val dialogBuilder = AlertDialog.Builder(act!!)
        dialogBuilder.setView(view)

        for (item in list) {
            val llColor = view.findViewById<LinearLayout>(item.key)
            llColor.setOnClickListener(this)
            lLayoutList[item.value.idColor] = llColor
        }

        alertDialog = dialogBuilder.create()
        alertDialog?.window?.setBackgroundDrawable(ColorDrawable(TRANSPARENT))
        alertDialog?.show()
    }

    override fun onClick(v: View?) {

        colorCallbacks.onColorReplacementIdRes(v?.id!!)
        alertDialog?.dismiss()
    }
}