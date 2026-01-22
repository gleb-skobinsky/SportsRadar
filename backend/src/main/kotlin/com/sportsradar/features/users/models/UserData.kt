package com.sportsradar.features.users.models

import org.sportsradar.sportsradarapp.shared.auth.data.UserData


fun UserData.toUpdatedUser(): UpdatedUser {
    return UpdatedUser(
        id = id,
        email = email,
        verified = verified
    )
}

fun ExistingUser.toUserData() = UserData(
    id = id,
    email = email,
    verified = verified
)