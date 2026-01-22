package org.sportsradar.sportsradarapp.auth.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class CheckOtpRequest(
    val login: String,
    val otp: String
)