package com.sportsradar.features.auth.models

import kotlinx.serialization.Serializable

@Serializable
data class RefreshToken(val token: String)