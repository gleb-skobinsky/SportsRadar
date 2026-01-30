package org.sportsradar.sportsradarapp.profile.data

import org.sportsradar.sportsradarapp.auth.data.UserSecureStorage
import org.sportsradar.sportsradarapp.common.network.ApiNetworkClient
import org.sportsradar.sportsradarapp.common.network.RequestResult
import org.sportsradar.sportsradarapp.common.network.mapOnSuccess
import org.sportsradar.sportsradarapp.profile.domain.UserUpdate
import org.sportsradar.sportsradarapp.shared.auth.data.UserData
import org.sportsradar.sportsradarapp.shared.auth.domain.toUser
import org.sportsradar.sportsradarapp.shared.common.data.Endpoints
import org.sportsradar.sportsradarapp.shared.profile.data.UpdateProfileRequest

class ProfileRepositoryImpl(
    private val client: ApiNetworkClient,
    private val storage: UserSecureStorage,
) : ProfileRepository {
    override suspend fun updateUserData(user: UserUpdate): RequestResult<Unit> {
        return client.put<UpdateProfileRequest, UserData>(
            urlPath = Endpoints.Profile.UpdateProfile,
            body = UpdateProfileRequest(
                firstName = user.firstName,
                lastname = user.lastName
            )
        ).mapOnSuccess { user ->
            storage.saveUserData(user.toUser())
            RequestResult.Success(Unit)
        }
    }

    override suspend fun checkSession(): RequestResult<Unit> {
        return client.get<UserData>(
            urlPath = Endpoints.Auth.CheckSession
        ).mapOnSuccess { user ->
            storage.saveUserData(user.toUser())
            RequestResult.Success(Unit)
        }
    }
}