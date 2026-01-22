package org.sportsradar.sportsradarapp.auth.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class OtpRequest(
    val login: String
)