package org.sportsradar.sportsradarapp.shared.common.data

@Suppress("ConstPropertyName")
object Endpoints {
    object Auth {
        const val Login = "/api/v1/login"
        const val Signup = "/api/v1/signup"
        const val RefreshToken = "/api/v1/refresh"
        const val CheckSession = "/api/v1/check-session"
    }

    object Notes {
        const val NoteIdParam = "noteId"
        const val GetNotes = "/api/v1/notes"
        const val CreateNote = "/api/v1/notes/create"
        const val DeleteNote = "/api/v1/notes/{$NoteIdParam}"
    }
}