package com.sportsradar.features.users.repository

import com.sportsradar.features.users.models.ExistingUser
import com.sportsradar.features.users.models.NewUser
import com.sportsradar.features.users.models.UpdatedUser

interface UsersRepository {

    suspend fun create(user: NewUser): String

    suspend fun readById(id: String): ExistingUser?

    suspend fun readByEmail(email: String): ExistingUser?

    suspend fun update(id: String, user: UpdatedUser)

    suspend fun delete(id: String)
}