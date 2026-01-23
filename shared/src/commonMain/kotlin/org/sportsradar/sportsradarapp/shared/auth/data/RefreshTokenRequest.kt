package org.sportsradar.sportsradarapp.shared.auth.data

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenRequest(val refreshToken: String)