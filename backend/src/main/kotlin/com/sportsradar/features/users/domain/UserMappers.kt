package com.sportsradar.features.users.domain

import org.sportsradar.sportsradarapp.shared.auth.data.UserData
import org.sportsradar.sportsradarapp.shared.profile.data.UpdateProfileRequest

fun UserData.toUpdatedUser(): UpdatedUser {
    return UpdatedUser(
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

fun UpdateProfileRequest.toUpdatedUser() = UpdatedUser(
    firstName = firstName,
    lastName = lastname
)