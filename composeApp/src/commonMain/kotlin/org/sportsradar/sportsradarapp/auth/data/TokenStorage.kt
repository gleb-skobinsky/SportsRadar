package org.sportsradar.sportsradarapp.auth.data

import org.sportsradar.sportsradarapp.storage.Storage
import kotlinx.coroutines.flow.Flow
import org.sportsradar.sportsradarapp.shared.auth.data.TokenData

private data object SecureStorageKeys {
    const val USER_EMAIL = "user_email"
    const val USER_ACCESS_TOKEN = "user_access_token"
    const val USER_REFRESH_TOKEN = "user_refresh_token"
}

interface UserSecureStorage {
    suspend fun saveEmail(email: String)

    suspend fun saveTokens(tokens: TokenData)

    fun getEmail(): Flow<String?>

    suspend fun getTokens(): TokenData?

    suspend fun clearAll()

    suspend fun getRefreshToken(): String?
}

class UserSecureStorageImpl(
    private val storage: Storage,
) : UserSecureStorage {
    override suspend fun saveEmail(email: String) {
        storage.setString(SecureStorageKeys.USER_EMAIL, email)
    }

    override suspend fun saveTokens(tokens: TokenData) {
        storage.setString(SecureStorageKeys.USER_ACCESS_TOKEN, tokens.accessToken)
        storage.setString(SecureStorageKeys.USER_REFRESH_TOKEN, tokens.refreshToken)
    }



    override fun getEmail(): Flow<String?> {
        return storage.subscribeToString(SecureStorageKeys.USER_EMAIL)
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