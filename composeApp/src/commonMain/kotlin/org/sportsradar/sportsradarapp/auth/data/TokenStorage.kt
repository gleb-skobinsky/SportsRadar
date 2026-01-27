package org.sportsradar.sportsradarapp.auth.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import org.sportsradar.sportsradarapp.shared.auth.data.TokenData
import org.sportsradar.sportsradarapp.shared.auth.domain.User
import org.sportsradar.sportsradarapp.storage.Storage

private data object SecureStorageKeys {
    const val USER_EMAIL = "user_email"
    const val USER_FIRST_NAME = "user_first_name"
    const val USER_LAST_NAME = "user_last_name"
    const val USER_ACCESS_TOKEN = "user_access_token"
    const val USER_REFRESH_TOKEN = "user_refresh_token"
}

interface UserSecureStorage {
    suspend fun saveUserData(user: User)

    suspend fun saveTokens(tokens: TokenData)

    fun getUserData(): Flow<User?>

    suspend fun getTokens(): TokenData?

    suspend fun clearAll()

    suspend fun getRefreshToken(): String?
}

class UserSecureStorageImpl(
    private val storage: Storage,
) : UserSecureStorage {
    override suspend fun saveUserData(user: User) {
        storage.setString(SecureStorageKeys.USER_EMAIL, user.email)
        storage.setString(SecureStorageKeys.USER_FIRST_NAME, user.firstName)
        storage.setString(SecureStorageKeys.USER_LAST_NAME, user.lastName)
    }

    override suspend fun saveTokens(tokens: TokenData) {
        storage.setString(SecureStorageKeys.USER_ACCESS_TOKEN, tokens.accessToken)
        storage.setString(SecureStorageKeys.USER_REFRESH_TOKEN, tokens.refreshToken)
    }


    override fun getUserData(): Flow<User?> {
        return combine(
            storage.subscribeToString(SecureStorageKeys.USER_EMAIL),
            storage.subscribeToString(SecureStorageKeys.USER_FIRST_NAME),
            storage.subscribeToString(SecureStorageKeys.USER_LAST_NAME)
        ) { email, firstName, lastName ->
            email?.let {
                User(
                    email = email,
                    firstName = firstName.orEmpty(),
                    lastName = lastName.orEmpty()
                )
            }
        }
    }

    override suspend fun getTokens(): TokenData? {
        val access = storage.getString(SecureStorageKeys.USER_ACCESS_TOKEN)
        val refresh = storage.getString(SecureStorageKeys.USER_REFRESH_TOKEN)
        return if (access != null && refresh != null) {
            TokenData(access, refresh)
        } else {
            null
        }
    }

    override suspend fun clearAll() {
        storage.clearAll()
    }

    override suspend fun getRefreshToken(): String? {
        return storage.getString(SecureStorageKeys.USER_ACCESS_TOKEN)
    }
}