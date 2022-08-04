package com.devsurfer.data.di

import com.devsurfer.data.repository.movie.dataSource.SearchRemoteDataSource
import com.devsurfer.data.service.SearchService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun provideSearchRemoteDataSource(service: SearchService): SearchRemoteDataSource =
        SearchRemoteDataSource(service)

}