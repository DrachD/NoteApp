package com.dmitriy.noteapp.utils

import androidx.annotation.IdRes

interface ListViewCallbacks {
    fun onListViewReplacement(@IdRes id: Int)
}