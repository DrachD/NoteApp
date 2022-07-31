package com.dmitriy.noteapp.model

import androidx.annotation.ColorRes
import androidx.annotation.IdRes

data class Color(
    @IdRes val idWrapContent: Int,
    @ColorRes val idColor: Int
)
