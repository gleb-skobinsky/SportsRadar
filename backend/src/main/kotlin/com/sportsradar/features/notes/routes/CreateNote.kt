package com.sportsradar.features.notes.routes

import com.sportsradar.features.notes.data.NotesRepository
import com.sportsradar.features.notes.data.dto.CreateNoteRequest
import com.sportsradar.jwt.JWTConfig
import com.sportsradar.jwt.emailByAuth
import com.sportsradar.shared.RepositoriesTags
import io.bkbn.kompendium.core.metadata.PostInfo
import io.bkbn.kompendium.core.plugin.NotarizedRoute
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.sportsradar.sportsradarapp.shared.common.data.Endpoints.Notes.CreateNote

internal fun Routing.createNoteRoute(
    repository: NotesRepository,
) {
    authenticate(JWTConfig.JWT_AUTH_ID) {
        route(CreateNote) {
            install(NotarizedRoute()) {
                tags = setOf(RepositoriesTags.NOTES)
                post = PostInfo.builder {
                    summary("Create a note")
                    description("The endpoint for note creation given a title and a body")
                    request {
                        description("Create a note")
                        requestType<CreateNoteRequest>()
                    }
                    response {
                        description("Note successfully created")
                        responseCode(HttpStatusCode.Created)
                        responseType<Boolean>()
                    }
                }
            }
            post {
                val email = call.emailByAuth() ?: run {
                    call.respond(
                        HttpStatusCode.Unauthorized,
                        "Request authentication failed"
                    )
                    return@post
                }
                val note = call.receive<CreateNoteRequest>()
                val created = repository.saveNote(
                    userEmail = email,
                    title = note.title,
                    body = note.body
                )
                if (created) {
                    call.respond(
                        HttpStatusCode.Created,
                        true
                    )
                } else {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }
        }
    }
}