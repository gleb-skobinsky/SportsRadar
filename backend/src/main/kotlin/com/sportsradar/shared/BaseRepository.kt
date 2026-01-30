package com.sportsradar.shared

import org.jetbrains.exposed.v1.core.StdOutSqlLogger
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction

abstract class BaseRepository {
    protected suspend inline fun <T> dbQuery(
        crossinline block: suspend () -> T
    ): T {
        return suspendTransaction {
            addLogger(StdOutSqlLogger)
            block()
        }
    }
}