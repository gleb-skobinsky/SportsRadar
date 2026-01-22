package com.sportsradar.features.users

import org.sportsradar.sportsradarapp.shared.auth.data.UserData
import com.sportsradar.shared.RepositoriesTags
import io.bkbn.kompendium.core.metadata.DeleteInfo
import io.bkbn.kompendium.core.metadata.GetInfo
import io.bkbn.kompendium.core.metadata.PutInfo
import io.bkbn.kompendium.core.plugin.NotarizedRoute
import io.bkbn.kompendium.json.schema.definition.TypeDefinition
import io.bkbn.kompendium.oas.payload.Parameter
import io.ktor.http.HttpStatusCode
import io.ktor.server.routing.Route

internal fun Route.usersEndpointDescription() {
    install(NotarizedRoute()) {
        tags = setOf(RepositoriesTags.USERS)
        get = GetInfo.builder {
            summary("Get a user")
            description("User retrieval endpoint")
            parameters(
                Parameter(
                    name = "id",
                    `in` = Parameter.Location.path,
                    schema = TypeDefinition.STRING
                )
            )
            response {
                description("User successfully retrieved")
                responseCode(HttpStatusCode.OK)
                responseType<UserData>()
            }
            canRespond {
                description("User not found")
                responseCode(HttpStatusCode.NotFound)
                responseType<Unit>()
            }
            canRespond {
                description("User request is inavlid")
                responseCode(HttpStatusCode.BadRequest)
                responseType<Unit>()
            }
        }
        put = PutInfo.builder {
            summary("Update a user")
            description("User update endpoint")
            parameters(
                Parameter(
                    name = "id",
                    `in` = Parameter.Location.path,
                    schema = TypeDefinition.STRING
                )
            )
            request {
                description("Update a user")
                requestType<UserData>()
            }
            response {
                description("User successfully updated")
                responseCode(HttpStatusCode.OK)
                responseType<Unit>()
            }
            canRespond {
                description("User update request is invalid")
                responseCode(HttpStatusCode.BadRequest)
                responseType<Unit>()
            }
        }
        delete = DeleteInfo.builder {
            summary("Delete a user")
            description("User deletion endpoint")
            parameters(
                Parameter(
                    name = "id",
                    `in` = Parameter.Location.path,
                    schema = TypeDefinition.STRING
                )
            )
            response {
                description("User successfully deleted")
                responseCode(HttpStatusCode.OK)
                responseType<Unit>()
            }
            canRespond {
                description("User deletion request is invalid")
                responseCode(HttpStatusCode.BadRequest)
                responseType<Unit>()
            }
        }
    }
}
