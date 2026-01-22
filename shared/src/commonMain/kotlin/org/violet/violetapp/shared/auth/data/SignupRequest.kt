package org.violet.violetapp.shared.auth.data

import kotlinx.serialization.Serializable

@Serializable
data class SignupRequest(
    val email: String,
    val password: String
)
