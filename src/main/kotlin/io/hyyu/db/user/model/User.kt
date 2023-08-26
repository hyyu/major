package io.hyyu.db.user.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

internal object UserProperties {
    const val USERNAME = "username"
    const val CLIENT_ID = "client_id"
    const val REFRESH_TOKEN = "refresh_token"
}

data class User(
    @BsonProperty(UserProperties.USERNAME)
    val username: String,
    @BsonProperty("password")
    val password: String,
    @BsonProperty(UserProperties.CLIENT_ID)
    var clientId: String,
    @BsonProperty(UserProperties.REFRESH_TOKEN)
    val refreshToken: String = "",
    @BsonProperty("salt")
    val salt: String,
    @BsonId
    val id: ObjectId = ObjectId()
)
