package com.dmitriy.noteapp.utils

import androidx.fragment.app.Fragment

interface FragmentCallbacks {
    fun onFragmentReplacement(fragment: Fragment, onBackPressed: Boolean, addToBackStack: Boolean)
}