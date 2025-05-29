package com.example.bitirmeprojesi.di

import com.example.bitirmeprojesi.data.datasource.YedirDataStore
import com.example.bitirmeprojesi.data.repo.YedirRepository
import com.example.bitirmeprojesi.retrofit.ApiUtils
import com.example.bitirmeprojesi.retrofit.YedirDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideYedirRepository(yedirDataStore: YedirDataStore): YedirRepository{
        return YedirRepository(yedirDataStore)
    }

    @Provides
    @Singleton
    fun provideYedirDataStore(yedirDao: YedirDao): YedirDataStore{
        return YedirDataStore(yedirDao)
    }

    @Provides
    @Singleton
    fun provideYedirDao(): YedirDao{
        return ApiUtils.getYedirDao()
    }
}