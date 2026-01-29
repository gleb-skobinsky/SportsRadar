package org.sportsradar.sportsradarapp.profile.data

import org.sportsradar.sportsradarapp.common.network.ApiNetworkClient
import org.sportsradar.sportsradarapp.common.network.RequestResult
import org.sportsradar.sportsradarapp.profile.domain.UserUpdate
import org.sportsradar.sportsradarapp.shared.common.data.Endpoints
import org.sportsradar.sportsradarapp.shared.profile.data.UpdateProfileRequest

class ProfileRepositoryImpl(
    private val client: ApiNetworkClient,
) : ProfileRepository {
    override suspend fun updateUserData(user: UserUpdate): RequestResult<Unit> {
        return client.post<UpdateProfileRequest, Unit>(
            urlPath = Endpoints.Profile.UpdateProfile,
            body = UpdateProfileRequest(
                firstName = user.firstName,
                lastname = user.lastName
            )
        )
    }
}