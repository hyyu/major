package io.cloudyhug.home.interactor

import io.cloudyhug.domain.usecase.user.GetUser
import javax.inject.Inject

class HomeInteractor @Inject constructor(
    private val getUser: GetUser
) {
    suspend fun getUser() = getUser.invoke()
}
