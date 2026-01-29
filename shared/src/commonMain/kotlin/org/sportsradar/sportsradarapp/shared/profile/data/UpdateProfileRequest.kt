package org.sportsradar.sportsradarapp.shared.profile.data

import kotlinx.serialization.Serializable

@Serializable
class UpdateProfileRequest(
    val firstName: String,
    val lastname: String,
)