package com.devsurfer.domain.repository.searchKeyword

import com.devsurfer.domain.model.searchKeyword.SearchKeyword

interface SearchKeywordRepository {
    suspend fun insertKeyWord(searchKeyword: String): Long
    suspend fun updateKeyword(searchKeyword: SearchKeyword): Int
    suspend fun deleteKeyword(searchKeyword: SearchKeyword): Int
    suspend fun getAll(): List<SearchKeyword>
    suspend fun getCurrentItems(): List<SearchKeyword>
    suspend fun isExistsKeyword(searchKeyword: String): Int
}