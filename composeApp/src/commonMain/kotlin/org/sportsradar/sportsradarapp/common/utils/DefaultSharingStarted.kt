package org.sportsradar.sportsradarapp.common.utils

import kotlinx.coroutines.flow.SharingStarted

internal val DefaultSharingStarted = SharingStarted.WhileSubscribed(5000)