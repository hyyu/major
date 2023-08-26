package io.hyyu.db.user.impl

import com.mongodb.client.model.Filters.eq
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import io.hyyu.db.user.UserDataSource
import io.hyyu.db.user.model.User
import io.hyyu.db.user.model.UserProperties
import kotlinx.coroutines.flow.firstOrNull

class MongoUserDataSource(
    db: MongoDatabase
) : UserDataSource {

    companion object {
        private const val USERS_COLLECTION = "users"
    }

    private val users = db.getCollection<User>(USERS_COLLECTION)

    override suspend fun getUserByClientId(clientId: String): User? = users.find(
        eq(UserProperties.CLIENT_ID, clientId)
    ).firstOrNull()

    override suspend fun getUserByUsername(username: String): User? = users.find(
        eq(User::username.name, username)
    ).firstOrNull()

    override suspend fun getUserByRefreshToken(refreshToken: String): User? = users.find(
        eq(UserProperties.REFRESH_TOKEN, refreshToken)
    ).firstOrNull()

    override suspend fun insertUser(user: User): Boolean = users.insertOne(user).wasAcknowledged()

}
