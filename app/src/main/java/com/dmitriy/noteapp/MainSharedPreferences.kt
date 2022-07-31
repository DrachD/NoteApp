package com.dmitriy.noteapp

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.dmitriy.noteapp.utils.ISharedPreferences

class MainSharedPreferences(
    private val act: MainActivity?
) : ISharedPreferences {

    val activity: MainActivity? = act

    override fun <T> saveData(key: String, data: T) {
        val sPref = act?.getPreferences(MODE_PRIVATE)!!
        val editor = sPref.edit()

        editor.apply {
            putData(key, data)
            apply()
        }
    }

    inline fun <reified T> loadData(key: String): Any {
        val sPref = activity?.getPreferences(MODE_PRIVATE)!!
        val savedData = sPref.getData<T>(key)

        return savedData as Any
    }
}

fun <T> Editor.putData(key: String, value: T) {

    when (value) {
        is Int -> putInt(key, value)
        is Boolean -> putBoolean(key, value)
        is String -> putString(key, value)
        is Float -> putFloat(key, value)
        is Long -> putLong(key, value)
    }


}

inline fun <reified T> SharedPreferences.getData(key: String): T {

    return when (T::class.java) {
        Int::class.java -> getInt(key, 0) as T
        Boolean::class.java -> getBoolean(key, false) as T
        String::class.java -> getString(key, null) as T
        Float::class.java -> getFloat(key, 0f) as T
        Long::class.java -> getLong(key, 0) as T
        else -> throw Exception("Unhandled return type")
    }
}