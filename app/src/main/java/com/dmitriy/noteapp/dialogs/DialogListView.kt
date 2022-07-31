package com.dmitriy.noteapp.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import com.dmitriy.noteapp.ListView
import com.dmitriy.noteapp.MainActivity
import com.dmitriy.noteapp.R
import com.dmitriy.noteapp.databinding.DialogUpdateListViewBinding
import com.dmitriy.noteapp.utils.ListViewCallbacks

class DialogListView(
    private val listViewCallbacks: ListViewCallbacks?,
    private val act: MainActivity?
) : View.OnClickListener {

    private var _binding: DialogUpdateListViewBinding? = null
    private val binding get() = _binding!!

    private var alertDialog: AlertDialog? = null

    private var lLayoutList = hashMapOf<Int, LinearLayout>()

    init {
        _binding = DialogUpdateListViewBinding.inflate(act?.layoutInflater!!)
    }

    fun showDialog() {
        val builder = AlertDialog.Builder(act!!)
        builder.setView(binding.root)

        for (item in ListView.list) {
            val linearLayout = binding.root.findViewById<LinearLayout>(item.key)
            linearLayout.setOnClickListener(this)
            lLayoutList[item.key] = linearLayout
        }

        alertDialog = builder.create()
        alertDialog?.show()
    }

    override fun onClick(v: View?) {
        listViewCallbacks?.onListViewReplacement(v?.id!!)
        alertDialog?.dismiss()
    }
}