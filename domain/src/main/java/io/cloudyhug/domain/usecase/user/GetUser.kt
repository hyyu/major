package io.cloudyhug.domain.usecase.user

import io.cloudyhug.data.repository.user.UserRepository
import io.cloudyhug.domain.usecase.user.model.User
import io.cloudyhug.domain.usecase.user.model.toModel
import javax.inject.Inject

class GetUser @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun invoke(): Result<User> = runCatching {
        userRepository.getUser().let { result ->
            result.exceptionOrNull()?.let { cause ->
                throw cause
            } ?: result.getOrNull()?.toModel()
            ?: throw Exception("User result is null")
        }
    }
}
