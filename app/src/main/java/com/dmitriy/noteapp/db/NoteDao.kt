package com.dmitriy.noteapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dmitriy.noteapp.model.Note

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNote(note: Note)

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note: Note)

    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE id=(:noteId)")
    fun getNote(noteId: Int): LiveData<Note>

    @Query("SELECT * FROM notes WHERE title LIKE :query OR body LIKE :query")
    fun searchNote(query: String?): LiveData<List<Note>>
}