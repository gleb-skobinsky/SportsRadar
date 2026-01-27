package com.sportsradar.features.users.domain

import org.sportsradar.sportsradarapp.shared.auth.data.UserData

fun UserData.toUpdatedUser(): UpdatedUser {
    return UpdatedUser(
        id = id,
        email = email,
        verified = verified,
        firstName = firstName,
        lastName = lastName,
    )
}

fun ExistingUser.toUserData() = UserData(
    id = id,
    email = email,
    verified = verified,
    firstName = firstName,
    lastName = lastName
)