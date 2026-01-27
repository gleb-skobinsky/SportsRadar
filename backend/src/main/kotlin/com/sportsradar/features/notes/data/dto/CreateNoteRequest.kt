package com.sportsradar.features.notes.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateNoteRequest(
    val title: String,
    val body: String
)