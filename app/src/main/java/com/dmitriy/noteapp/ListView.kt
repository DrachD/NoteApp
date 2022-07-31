package com.dmitriy.noteapp

import android.util.Log
import androidx.annotation.DrawableRes

class ListView {

    companion object {
        val list: HashMap<Int, Int> = HashMap()
        @DrawableRes
        var currentListView: Int = R.drawable.ic_list

        init {
            Log.d("myLogs", "init")

            list[R.id.llList] = R.drawable.ic_list
            list[R.id.llDetails] = R.drawable.ic_details
            list[R.id.llTile] = R.drawable.ic_tile
            list[R.id.llBigTile] = R.drawable.ic_big_tile
        }
    }
}