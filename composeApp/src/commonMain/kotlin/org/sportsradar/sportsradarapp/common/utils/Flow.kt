package org.sportsradar.sportsradarapp.common.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.runningFold

data class History<T>(val previous: T?, val current: T)

// emits null, History(null,1), History(1,2)...
fun <T> Flow<T>.runningHistory(): Flow<History<T>> =
    runningFold(
        initial = null as (History<T>?),
        operation = { accumulator, new -> History(accumulator?.current, new) }
    ).filterNotNull()

fun <T, R> Flow<T>.mapDistinctBy(transform: (T) -> R) = distinctUntilChangedBy(transform).map(transform)
