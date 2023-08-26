package io.cloudyhug.data.repository.user.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.cloudyhug.data.repository.user.UserRepository
import io.cloudyhug.data.repository.user.impl.UserRepositoryImpl
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserRepositoryModule {
    @Provides
    @Singleton
    fun provideUserRepository(client: HttpClient): UserRepository = UserRepositoryImpl(client)
}
