package com.devsurfer.data.di

import android.content.Context
import androidx.room.Room
import com.devsurfer.data.database.AppDatabase
import com.devsurfer.data.repository.searchKeyword.dataSource.SearchKeywordDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "movieSearch_db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideSearchKeywordDao(database: AppDatabase): SearchKeywordDao = database.searchKeywordDao()
}