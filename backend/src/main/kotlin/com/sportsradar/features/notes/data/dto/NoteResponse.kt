package com.sportsradar.features.notes.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class NoteResponse(
    val id: String,
    val title: String,
    val body: String
)