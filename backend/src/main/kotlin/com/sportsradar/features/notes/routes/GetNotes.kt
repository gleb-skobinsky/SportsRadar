package com.sportsradar.features.notes.routes

import com.sportsradar.features.notes.data.NotesRepository
import com.sportsradar.features.notes.data.dto.NoteResponse
import com.sportsradar.jwt.JWTConfig
import com.sportsradar.jwt.emailByAuth
import com.sportsradar.shared.RepositoriesTags
import io.bkbn.kompendium.core.metadata.GetInfo
import io.bkbn.kompendium.core.plugin.NotarizedRoute
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import org.sportsradar.sportsradarapp.shared.common.data.Endpoints.Notes.GetNotes

internal fun Routing.getNotesRoute(
    repository: NotesRepository,
) {
    authenticate(JWTConfig.JWT_AUTH_ID) {
        route(GetNotes) {
            install(NotarizedRoute()) {
                tags = setOf(RepositoriesTags.NOTES)
                get = GetInfo.builder {
                    summary("Notes for user")
                    description("All notes for the given user")
                    response {
                        description("Notes list successfully retrieved")
                        responseCode(HttpStatusCode.OK)
                        responseType<List<NoteResponse>>()
                    }
                }
            }
            get {
                val email = call.emailByAuth() ?: run {
                    call.respond(
                        HttpStatusCode.Unauthorized,
                        "Request authentication failed"
                    )
                    return@get
                }
                val notes = repository.getNotesForUser(email)
                call.respond(
                    status = HttpStatusCode.OK,
                    message = notes
                )
            }
        }
    }
}