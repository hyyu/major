package io.hyyu.db.user

import io.hyyu.db.user.model.User

interface UserDataSource {
    suspend fun getUserByClientId(clientId: String): User?
    suspend fun getUserByUsername(username: String): User?
    suspend fun getUserByRefreshToken(refreshToken: String): User?
    suspend fun insertUser(user: User): Boolean
}
