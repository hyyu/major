package io.cloudyhug.data.repository.user

import io.cloudyhug.data.network.model.response.user.GetUserResponseDto

interface UserRepository {
    suspend fun getUser(): Result<GetUserResponseDto>
}
