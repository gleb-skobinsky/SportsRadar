package com.sportsradar.features.users.domain

data class UpdatedUser(
    val id: String,
    val email: String,
    val verified: Boolean,
    val firstName: String,
    val lastName: String,
)
