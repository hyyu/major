package io.cloudyhug.data.repository.credentials.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.cloudyhug.data.repository.preference.Preference
import io.cloudyhug.data.repository.credentials.CredentialsGenerator
import io.cloudyhug.data.repository.credentials.impl.CredentialsGeneratorImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CredentialsModule {

    @Provides
    @Singleton
    fun provideCredentialsGenerator(prefs: Preference): CredentialsGenerator = CredentialsGeneratorImpl(prefs)

}