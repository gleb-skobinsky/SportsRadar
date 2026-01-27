package org.sportsradar.sportsradarapp.shared.auth.data

import kotlinx.serialization.Serializable

@Serializable
data class UserLoginResponse(
    val tokens: TokenData,
    val user: UserData,
)
