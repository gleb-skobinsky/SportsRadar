package com.sportsradar.features.notes.routes

import com.sportsradar.features.notes.data.NotesRepository
import com.sportsradar.jwt.JWTConfig
import com.sportsradar.shared.RepositoriesTags
import org.sportsradar.sportsradarapp.shared.common.data.Endpoints.Notes.DeleteNote
import org.sportsradar.sportsradarapp.shared.common.data.Endpoints.Notes.NoteIdParam
import io.bkbn.kompendium.core.metadata.DeleteInfo
import io.bkbn.kompendium.core.plugin.NotarizedRoute
import io.bkbn.kompendium.json.schema.definition.TypeDefinition
import io.bkbn.kompendium.oas.payload.Parameter
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.delete
import io.ktor.server.routing.route

internal fun Routing.deleteNoteRoute(
    repository: NotesRepository,
) {
    authenticate(JWTConfig.JWT_AUTH_ID) {
        route(DeleteNote) {
            install(NotarizedRoute()) {
                tags = setOf(RepositoriesTags.NOTES)
                delete = DeleteInfo.builder {
                    summary("Delete a note")
                    description("The endpoint for note deletion given its id")
                    parameters(
                        Parameter(
                            name = NoteIdParam,
                            `in` = Parameter.Location.path,
                            schema = TypeDefinition.STRING
                        )
                    )
                    response {
                        description("Note successfully deleted")
                        responseCode(HttpStatusCode.OK)
                        responseType<Boolean>()
                    }
                }
            }
            delete {
                val id = call.parameters[NoteIdParam] ?: run {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        "Invalid id"
                    )
                    return@delete
                }
                val deleted = repository.deleteNote(id)
                if (deleted) {
                    call.respond(
                        HttpStatusCode.OK,
                        true
                    )
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
        }
    }
}