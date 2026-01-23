package org.sportsradar.sportsradarapp.shared.auth.domain

import org.sportsradar.sportsradarapp.shared.auth.data.TokenData

data class Tokens(
    val accessToken: String,
    val refreshToken: String,
)

fun TokenData.toTokens(): Tokens = Tokens(accessToken, refreshToken)