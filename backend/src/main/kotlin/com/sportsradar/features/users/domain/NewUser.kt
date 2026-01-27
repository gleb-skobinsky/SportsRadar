package com.sportsradar.features.users.domain

data class NewUser(
    val email: String,
    val password: String,
    val verified: Boolean
)
