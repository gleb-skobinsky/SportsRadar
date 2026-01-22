package com.sportsradar.features.notes

import com.sportsradar.features.notes.repository.NotesRepository
import com.sportsradar.features.notes.routes.createNoteRoute
import com.sportsradar.features.notes.routes.deleteNoteRoute
import com.sportsradar.features.notes.routes.getNotesRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureNotes(
    repository: NotesRepository
) {
    routing {
        getNotesRoute(repository)
        createNoteRoute(repository)
        deleteNoteRoute(repository)
    }
}