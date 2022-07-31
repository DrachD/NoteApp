package com.dmitriy.noteapp.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.dmitriy.noteapp.db.NoteDatabase
import com.dmitriy.noteapp.model.Note
import java.lang.IllegalStateException
import java.util.concurrent.Executors

class NoteRepository(context: Context) {

    private val database: NoteDatabase = Room.databaseBuilder(
        context.applicationContext,
        NoteDatabase::class.java,
        "note_db"
    ).build()

    private val noteDao = database.noteDao()

    private val executor = Executors.newSingleThreadExecutor()

    fun addNote(note: Note) {
        executor.execute {
            noteDao.addNote(note)
        }
    }

    fun updateNote(note: Note) {
        executor.execute {
            noteDao.updateNote(note)
        }
    }

    fun deleteNote(note: Note) {
        executor.execute {
            noteDao.deleteNote(note)
        }
    }

    fun getNote(noteId: Int): LiveData<Note> = noteDao.getNote(noteId)

    fun getAllNotes(): LiveData<List<Note>> = noteDao.getAllNotes()

    fun searchNote(query: String?): LiveData<List<Note>> = noteDao.searchNote(query)

    companion object {
        private var INSTANCE: NoteRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = NoteRepository(context)
            }
        }

        fun get(): NoteRepository {
            return INSTANCE ?: throw IllegalStateException("NoteRepository must be initialized")
        }
    }
}