package com.breuk.test.journey.di

import android.app.Application
import androidx.room.Room
import com.breuk.test.journey.R
import com.breuk.test.journey.data.local.PostDatabase
import com.breuk.test.journey.data.remote.JsonPlaceholderApi
import com.breuk.test.journey.data.repository.JsonPlaceholderRepositoryImpl
import com.breuk.test.journey.domain.repository.JsonPlaceholderRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    @Singleton
    fun provideJsonPlaceholderRepository(
        api: JsonPlaceholderApi,
        postDatabase: PostDatabase
    ): JsonPlaceholderRepository {
        return JsonPlaceholderRepositoryImpl(api, postDatabase.dao)
    }

    @Provides
    @Singleton
    fun providePostDatabase(application: Application): PostDatabase {
        return Room.databaseBuilder(
            application,
            PostDatabase::class.java,
            application.getString(R.string.post_db)
        ).build()
    }


}