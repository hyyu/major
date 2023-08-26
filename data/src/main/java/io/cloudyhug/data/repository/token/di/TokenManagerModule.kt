package io.cloudyhug.data.repository.auth.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.cloudyhug.data.repository.preference.Preference
import io.cloudyhug.data.repository.token.TokenManager
import io.cloudyhug.data.repository.token.impl.TokenManagerImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TokenManagerModule {

    @Provides
    @Singleton
    fun provideTokenManager(preference: Preference): TokenManager = TokenManagerImpl(preference)

}
