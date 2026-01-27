package com.sportsradar.shared

import kotlin.uuid.Uuid

@Suppress("NOTHING_TO_INLINE")
inline fun String.uuid(): Uuid = Uuid.parse(this)