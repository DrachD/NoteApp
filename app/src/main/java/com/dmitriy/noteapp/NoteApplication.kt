package com.dmitriy.noteapp

import android.app.Application
import com.dmitriy.noteapp.repository.NoteRepository

class NoteApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        NoteRepository.initialize(this)
    }
}