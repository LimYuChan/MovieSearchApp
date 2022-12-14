package com.devsurfer.data.di

import com.devsurfer.data.repository.movie.SearchRepositoryImpl
import com.devsurfer.data.repository.searchKeyword.SearchKeywordRepositoryImpl
import com.devsurfer.data.repository.searchKeyword.dataSource.SearchKeywordDao
import com.devsurfer.data.service.SearchService
import com.devsurfer.domain.repository.movie.SearchRepository
import com.devsurfer.domain.repository.searchKeyword.SearchKeywordRepository
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
    fun provideSearchRepository(service: SearchService): SearchRepository =
        SearchRepositoryImpl(service)

    @Singleton
    @Provides
    fun provideSearchKeywordRepository(dao: SearchKeywordDao): SearchKeywordRepository =
        SearchKeywordRepositoryImpl(dao)
}