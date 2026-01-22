package com.sportsradar.features.notes.models

import kotlinx.serialization.Serializable

@Serializable
data class NoteResponse(
    val id: String,
    val title: String,
    val body: String
)
