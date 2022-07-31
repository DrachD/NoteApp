package com.dmitriy.noteapp.utils

interface ISharedPreferences {
    fun <T> saveData(key: String, data: T)
}