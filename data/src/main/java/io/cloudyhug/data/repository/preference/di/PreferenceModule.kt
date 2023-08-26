package io.cloudyhug.data.repository.preference.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.cloudyhug.data.repository.preference.Preference
import io.cloudyhug.data.repository.preference.impl.PreferenceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {

    private const val PREF_FILENAME = "PREF_MAJOR"

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_FILENAME, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providePreference(sharedPrefs: SharedPreferences): Preference = PreferenceImpl(sharedPrefs)

}
