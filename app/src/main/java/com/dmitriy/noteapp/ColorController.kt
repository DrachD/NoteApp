package com.dmitriy.noteapp

import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.toColor
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dmitriy.noteapp.databinding.DialogUpdateColorBinding
import com.dmitriy.noteapp.dialogs.DialogColor
import com.dmitriy.noteapp.fragments.AddNoteFragment
import com.dmitriy.noteapp.model.Color
import com.dmitriy.noteapp.utils.ColorCallbacks
import com.dmitriy.noteapp.viewmodel.ColorListViewModel

class ColorController(
    private val colorCallbacks: ColorCallbacks,
    private val act: MainActivity
) : ColorCallbacks {

    private var _binding: DialogUpdateColorBinding? = null
    private val binding get() = _binding!!
    private var activity: MainActivity? = null
    private var dialogColor: DialogColor? = null

    var colorList = hashMapOf<Int, Color>()

    @ColorRes
    var currentColor: Int = R.color.dialog_white

    val wrapSelected get() = R.drawable.my_custom_background
    val wrapUnselected get() = R.color.dialog_white

    init {
        _binding = DialogUpdateColorBinding.inflate(act.layoutInflater)
        activity = act

        colorList[R.id.color_red] = Color(R.id.wrap_content_red, R.color.dialog_red)
        colorList[R.id.color_orange] = Color(R.id.wrap_content_orange, R.color.dialog_orange)
        colorList[R.id.color_yellow] = Color(R.id.wrap_content_yellow, R.color.dialog_yellow)
        colorList[R.id.color_green] = Color(R.id.wrap_content_green, R.color.dialog_green)
        colorList[R.id.color_blue] = Color(R.id.wrap_content_blue, R.color.dialog_blue)
        colorList[R.id.color_purple] = Color(R.id.wrap_content_purple, R.color.dialog_purple)
        colorList[R.id.color_black] = Color(R.id.wrap_content_black, R.color.dialog_black)
        colorList[R.id.color_gray] = Color(R.id.wrap_content_gray, R.color.dialog_gray)
        colorList[R.id.color_white] = Color(R.id.wrap_content_white, R.color.dialog_white)
    }

    fun updateUI() {
        for (item in colorList) {
            val llWrapContent = binding.root.findViewById<LinearLayout>(item.value.idWrapContent)

            llWrapContent.background = if ((item.value.idColor) == currentColor) {
                activity?.getDrawable(wrapSelected)
            } else {
                activity?.getDrawable(wrapUnselected)
            }
        }
    }

    fun showDialog(@ColorRes id: Int) {
        currentColor = id
        updateUI()
        dialogColor = DialogColor(this)
        dialogColor?.showDialog(activity, binding.root, colorList)
    }

    override fun onColorReplacementIdRes(@IdRes idColor: Int) {
        changeColor(idColor)
    }

    private fun changeColor(@IdRes idColor: Int) {
        colorCallbacks.onColorReplacementColorRes(colorList[idColor]?.idColor!!)
    }

    override fun onColorReplacementColorRes(idColor: Int) {

    }
}