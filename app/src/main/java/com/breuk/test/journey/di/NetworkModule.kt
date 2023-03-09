package com.breuk.test.journey.di

import com.breuk.test.journey.data.remote.JsonPlaceholderApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideJsonPlaceHolderApi(): JsonPlaceholderApi {
        return Retrofit.Builder()
            .baseUrl(JsonPlaceholderApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(JsonPlaceholderApi::class.java)
    }
}