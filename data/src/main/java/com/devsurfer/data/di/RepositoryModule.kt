package com.devsurfer.data.di

import com.devsurfer.data.repository.movie.SearchRepositoryImpl
import com.devsurfer.data.repository.movie.dataSource.SearchRemoteDataSource
import com.devsurfer.domain.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideSearchRepository(dataSource: SearchRemoteDataSource): SearchRepository =
        SearchRepositoryImpl(dataSource)
}