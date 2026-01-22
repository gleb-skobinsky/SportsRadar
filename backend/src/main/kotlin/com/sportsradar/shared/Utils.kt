package com.sportsradar.shared

import java.util.*

@Suppress("NOTHING_TO_INLINE")
inline fun String.uuid(): UUID = UUID.fromString(this)