package com.devsurfer.data.repository.searchKeyword

import com.devsurfer.data.mapper.searchKeyword.toEntity
import com.devsurfer.data.mapper.searchKeyword.toModel
import com.devsurfer.data.model.searchKeyword.SearchKeywordEntity
import com.devsurfer.data.repository.searchKeyword.dataSource.SearchKeywordDao
import com.devsurfer.domain.model.searchKeyword.SearchKeyword
import com.devsurfer.domain.repository.searchKeyword.SearchKeywordRepository
import javax.inject.Inject

//SearchKeywordRepository 참고
class SearchKeywordRepositoryImpl @Inject constructor(
    private val dao: SearchKeywordDao
): SearchKeywordRepository{

    override suspend fun insertKeyWord(searchKeyword: String): Long =
        dao.insert(SearchKeywordEntity(search_keyword = searchKeyword))

    override suspend fun updateKeyword(searchKeyword: SearchKeyword): Int =
        dao.update(searchKeyword.toEntity())

    override suspend fun deleteKeyword(searchKeyword: SearchKeyword): Int =
        dao.update(searchKeyword.toEntity())

    override suspend fun getAll(): List<SearchKeyword> =
        dao.getAll().map { it.toModel() }

    override suspend fun getCurrentItems(): List<SearchKeyword> =
        dao.getCurrentItems().map { it.toModel() }

    override suspend fun isExistsKeyword(searchKeyword: String): Int =
        dao.isExistsKeyword(searchKeyword)
}