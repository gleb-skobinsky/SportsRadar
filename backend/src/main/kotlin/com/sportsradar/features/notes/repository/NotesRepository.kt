package com.sportsradar.features.notes.repository

import com.sportsradar.features.notes.models.NoteResponse

interface NotesRepository {
    suspend fun getNotesForUser(email: String): List<NoteResponse>

    suspend fun saveNote(
        userEmail: String,
        title: String,
        body: String
    ): Boolean

    suspend fun deleteNote(noteId: String): Boolean
}