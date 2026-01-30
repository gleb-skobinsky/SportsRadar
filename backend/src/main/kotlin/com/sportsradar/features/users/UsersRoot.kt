package com.sportsradar.features.users

import com.sportsradar.features.users.data.UsersRepository
import com.sportsradar.features.users.domain.toUpdatedUser
import com.sportsradar.features.users.domain.toUserData
import com.sportsradar.jwt.JWTConfig.Companion.JWT_AUTH_ID
import com.sportsradar.jwt.emailByAuth
import com.sportsradar.shared.RepositoriesTags
import io.bkbn.kompendium.core.metadata.PostInfo
import io.bkbn.kompendium.core.plugin.NotarizedRoute
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import org.sportsradar.sportsradarapp.shared.auth.data.UserData
import org.sportsradar.sportsradarapp.shared.common.data.Endpoints
import org.sportsradar.sportsradarapp.shared.profile.data.UpdateProfileRequest

fun Application.configureUsersRoutes(
    usersRepository: UsersRepository
) {
    routing {
        // Read user
        authenticate(JWT_AUTH_ID) {
            route(Endpoints.Profile.UpdateProfile) {
                install(NotarizedRoute()) {
                    tags = setOf(RepositoriesTags.PROFILE)
                    post = PostInfo.builder {
                        summary("Profile update")
                        description("Update profile of the authenticated user")
                        request {
                            description("Update a user profile")
                            requestType<UpdateProfileRequest>()
                        }
                        response {
                            description("User successfully logged in")
                            responseCode(HttpStatusCode.Created)
                            responseType<UserData>()
                        }
                    }
                }
                put {
                    val user = call.receive<UpdateProfileRequest>()
                    val userEmail = call.emailByAuth() ?: run {
                        call.respond(HttpStatusCode.Unauthorized)
                        return@put
                    }
                    println("Updating by email in DB")
                    val newUser = usersRepository.updateByEmail(userEmail, user.toUpdatedUser())
                    println("Updated by email in DB: $newUser")
                    if (newUser != null) {
                        call.respond(HttpStatusCode.OK, newUser.toUserData())
                    } else {
                        call.respond(HttpStatusCode.NotFound)
                    }
                }
            }
            route("/users/{id}") {
                usersEndpointDescription()
                get {
                    val id = call.parameters["id"] ?: run {
                        call.respond(
                            HttpStatusCode.BadRequest,
                            "Invalid id"
                        )
                        return@get
                    }
                    val user = usersRepository.readById(id)
                    if (user != null) {
                        call.respond(HttpStatusCode.OK, user)
                    } else {
                        call.respond(HttpStatusCode.NotFound)
                    }
                }
                put {
                    val id = call.parameters["id"] ?: run {
                        call.respond(
                            HttpStatusCode.BadRequest,
                            "Invalid id"
                        )
                        return@put
                    }
                    val user = call.receive<UserData>()
                    usersRepository.update(id, user.toUpdatedUser())
                    call.respond(HttpStatusCode.OK)
                }
                delete {
                    val id = call.parameters["id"] ?: run {
                        call.respond(
                            HttpStatusCode.BadRequest,
                            "Invalid id"
                        )
                        return@delete
                    }
                    usersRepository.delete(id)
                    call.respond(HttpStatusCode.OK)
                }
            }
        }
    }
}


