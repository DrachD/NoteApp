package com.dmitriy.noteapp.model

import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var title: String,
    var body: String,
    @ColorRes var resColor: Int
)