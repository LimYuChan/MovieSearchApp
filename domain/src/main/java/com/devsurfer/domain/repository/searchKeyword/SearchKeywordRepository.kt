package com.devsurfer.domain.repository.searchKeyword

import com.devsurfer.domain.model.searchKeyword.SearchKeyword

interface SearchKeywordRepository {
    suspend fun insertKeyWord(searchKeyword: String): Long
    suspend fun updateKeyword(searchKeyword: SearchKeyword): Int
    suspend fun deleteKeyword(searchKeyword: SearchKeyword): Int
    suspend fun getAll(): List<SearchKeyword>
    //최근 검색한 10개의 아이템을 가져옵니다.
    suspend fun getCurrentItems(): List<SearchKeyword>
    //입력한 키워드를 데이터 베이스에 넣기전에 최근 검색했던 객체 10개 중 하나라면 추가하지 않기 위해 확인하도록 합니다.
    suspend fun isExistsKeyword(searchKeyword: String): Int
}