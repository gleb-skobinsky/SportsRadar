package com.sportsradar.db

import com.sportsradar.features.auth.data.tables.RevokedTokensTable
import com.sportsradar.features.notes.data.tables.NotesTable
import com.sportsradar.features.users.data.UsersTable

internal val TablesRegistry = arrayOf(
    UsersTable,
    RevokedTokensTable,
    NotesTable
)