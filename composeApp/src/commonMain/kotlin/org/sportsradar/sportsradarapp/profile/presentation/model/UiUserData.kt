package org.sportsradar.sportsradarapp.profile.presentation.model

import androidx.compose.runtime.Immutable
import org.sportsradar.sportsradarapp.profile.domain.UserUpdate

@Immutable
data class UiUserData(
    val firstName: String,
    val lastName: String,
    val email: String,
)

fun UiUserData.toUserUpdate() = UserUpdate(
    firstName = firstName,
    lastName = lastName
)