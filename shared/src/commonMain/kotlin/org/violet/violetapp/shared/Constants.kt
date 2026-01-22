package org.violet.violetapp.shared

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json


@OptIn(ExperimentalSerializationApi::class)
val DefaultJson = Json {
    ignoreUnknownKeys = true
    encodeDefaults = false
    allowTrailingComma = true
}