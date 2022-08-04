package com.devsurfer.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devsurfer.data.model.searchKeyword.SearchKeywordEntity
import com.devsurfer.data.repository.searchKeyword.dataSource.SearchKeywordDao

@Database(
    entities = [SearchKeywordEntity::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase(){
    abstract fun searchKeywordDao(): SearchKeywordDao
}