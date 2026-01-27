package com.sportsradar.features.users.domain

data class ExistingUser(
    val id: String,
    val email: String,
    val password: String,
    val verified: Boolean,
    val firstName: String,
    val lastName: String,
)