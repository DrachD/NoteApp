package com.dmitriy.noteapp.utils

import androidx.annotation.ColorRes
import androidx.annotation.IdRes

interface ColorCallbacks {
    fun onColorReplacementColorRes(@ColorRes idColor: Int)
    fun onColorReplacementIdRes(@IdRes idColor: Int)
}