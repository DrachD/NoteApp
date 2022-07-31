package com.dmitriy.noteapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.dmitriy.noteapp.model.Note
import com.dmitriy.noteapp.repository.NoteRepository

class NoteListViewModel : ViewModel() {

    val noteRepository = NoteRepository.get()
    val noteListLiveData = noteRepository.getAllNotes()

    private val noteIdLiveData = MutableLiveData<Int>()

    var noteLiveData: LiveData<Note> = Transformations.switchMap(noteIdLiveData) { noteId ->
        noteRepository.getNote(noteId)
    }

    fun loadNote(noteId: Int) {
        noteIdLiveData.value = noteId
    }
}