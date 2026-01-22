package com.violet.features.notes.routes

import com.violet.features.notes.models.NoteResponse
import com.violet.features.notes.repository.NotesRepository
import com.violet.jwt.JWTConfig
import com.violet.jwt.email
import com.violet.shared.RepositoriesTags
import org.violet.violetapp.shared.common.data.Endpoints.Notes.GetNotes
import io.bkbn.kompendium.core.metadata.GetInfo
import io.bkbn.kompendium.core.plugin.NotarizedRoute
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.route

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
                val email = call.principal<JWTPrincipal>()?.email ?: run {
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