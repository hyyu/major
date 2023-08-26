package io.cloudyhug.data.repository.auth.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.cloudyhug.data.repository.auth.AuthRepository
import io.cloudyhug.data.repository.auth.impl.AuthRepositoryImpl
import io.cloudyhug.data.repository.token.TokenManager
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthRepositoryModule {
    @Provides
    @Singleton
    fun provideAuthRepository(
        client: HttpClient,
        tokenManager: TokenManager
    ): AuthRepository = AuthRepositoryImpl(
        client = client
    )
}
