package io.cloudyhug.data.repository.user.impl

import io.cloudyhug.data.network.model.response.user.GetUserResponseDto
import io.cloudyhug.data.network.route.Endpoints
import io.cloudyhug.data.repository.user.UserRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val client: HttpClient
) : UserRepository {
    override suspend fun getUser(): Result<GetUserResponseDto> =
        runCatching {
            withContext(Dispatchers.IO){
                client.get(Endpoints.USER).body()
            }
        }
}
