package com.example.notesapp.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.Model.Note
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.firstOrNull

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {
    val allNotes = repository.allNotes

    fun insert(note: Note) = viewModelScope.launch {
        repository.insert(note)
    }

    fun update(note: Note) = viewModelScope.launch {
        repository.update(note)
    }

    fun delete(note: Note) = viewModelScope.launch {
        repository.delete(note)
    }

    suspend fun getNoteById(id: Int): Note? {
        return repository.getNoteById(id).firstOrNull()
    }
}