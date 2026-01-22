package org.violet.violetapp.auth.data

import org.violet.violetapp.storage.Storage
import kotlinx.coroutines.flow.Flow

private data object SecureStorageKeys {
    const val USER_EMAIL = "user_email"
    const val USER_ACCESS_TOKEN = "user_access_token"
}

interface UserSecureStorage {
    suspend fun saveEmail(email: String)

    suspend fun saveToken(token: String)

    fun getEmail(): Flow<String?>

    fun getToken(): Flow<String?>

    suspend fun clearAll()
}

class UserSecureStorageImpl(
    private val storage: Storage,
) : UserSecureStorage {
    override suspend fun saveEmail(email: String) {
        storage.setString(SecureStorageKeys.USER_EMAIL, email)
    }

    override suspend fun saveToken(token: String) {
        storage.setString(SecureStorageKeys.USER_ACCESS_TOKEN, token)
    }

    override fun getEmail(): Flow<String?> {
        return storage.subscribeToString(SecureStorageKeys.USER_EMAIL)
    }

    override fun getToken(): Flow<String?> {
        return storage.subscribeToString(SecureStorageKeys.USER_ACCESS_TOKEN)
    }

    override suspend fun clearAll() {
        storage.clearAll()
    }
}