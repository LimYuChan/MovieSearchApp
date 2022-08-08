package com.devsurfer.data.repository.searchKeyword.dataSource

import androidx.room.*
import com.devsurfer.data.model.searchKeyword.SearchKeywordEntity

//SearchKeywordRepository 참고
@Dao
interface SearchKeywordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(searchKeywordEntity: SearchKeywordEntity): Long

    @Update
    suspend fun update(searchKeywordEntity: SearchKeywordEntity): Int

    @Delete
    suspend fun delete(searchKeywordEntity: SearchKeywordEntity): Int

    @Query("SELECT * FROM recent_search ORDER BY id DESC")
    suspend fun getAll(): List<SearchKeywordEntity>

    @Query("SELECT * FROM recent_search ORDER BY id DESC LIMIT 10")
    suspend fun getCurrentItems(): List<SearchKeywordEntity>

    @Query("SELECT COUNT(id) FROM recent_search WHERE search_keyword = :searchKeyword ORDER BY id DESC LIMIT 10")
    suspend fun isExistsKeyword(searchKeyword: String): Int
}