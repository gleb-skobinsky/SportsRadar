package org.sportsradar.sportsradarapp.profile.data

import org.sportsradar.sportsradarapp.common.network.RequestResult
import org.sportsradar.sportsradarapp.profile.domain.UserUpdate

interface ProfileRepository {

    suspend fun updateUserData(user: UserUpdate): RequestResult<Unit>

    suspend fun checkSession(): RequestResult<Unit>
}