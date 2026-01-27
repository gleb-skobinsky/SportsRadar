package org.sportsradar.sportsradarapp.shared.auth.domain

import org.sportsradar.sportsradarapp.shared.auth.data.UserData

data class User(
    val email: String,
    val firstName: String,
    val lastName: String,
)

fun UserData.toUser() = User(
    email = email,
    firstName = firstName,
    lastName = lastName
)