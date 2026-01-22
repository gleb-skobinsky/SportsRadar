package org.sportsradar.sportsradarapp.shared.auth.data

import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    val id: String,
    val email: String,
    val verified: Boolean
)