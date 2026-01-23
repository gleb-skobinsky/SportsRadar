package org.sportsradar.sportsradarapp.shared.auth.data

import kotlinx.serialization.Serializable

@Serializable
class UserLogoutRequest(
    val refreshToken: String,
)