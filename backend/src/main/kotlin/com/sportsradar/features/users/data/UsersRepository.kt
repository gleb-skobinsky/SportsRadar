package com.sportsradar.features.users.data

import com.sportsradar.features.users.domain.ExistingUser
import com.sportsradar.features.users.domain.NewUser
import com.sportsradar.features.users.domain.UpdatedUser
import org.sportsradar.sportsradarapp.shared.auth.data.UserData

interface UsersRepository {

    suspend fun create(user: NewUser): String

    suspend fun readById(id: String): ExistingUser?

    suspend fun readByEmail(email: String): ExistingUser?

    suspend fun update(id: String, user: UpdatedUser): ExistingUser?

    suspend fun updateByEmail(email: String, user: UpdatedUser): ExistingUser?

    suspend fun delete(id: String)
}