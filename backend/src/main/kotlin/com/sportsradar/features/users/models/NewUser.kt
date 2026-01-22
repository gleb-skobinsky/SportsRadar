package com.sportsradar.features.users.models

data class NewUser(
    val email: String,
    val password: String,
    val verified: Boolean
)

data class ExistingUser(
    val id: String,
    val email: String,
    val password: String,
    val verified: Boolean
)

data class UpdatedUser(
    val id: String,
    val email: String,
    val verified: Boolean
)