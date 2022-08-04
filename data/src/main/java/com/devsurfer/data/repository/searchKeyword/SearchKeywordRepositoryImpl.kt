package com.devsurfer.data.repository.searchKeyword

import com.devsurfer.data.mapper.searchKeyword.SearchKeywordMapper
import com.devsurfer.data.model.searchKeyword.SearchKeywordEntity
import com.devsurfer.data.repository.searchKeyword.dataSource.SearchKeywordDao
import com.devsurfer.domain.model.searchKeyword.SearchKeyword
import com.devsurfer.domain.repository.searchKeyword.SearchKeywordRepository
import javax.inject.Inject

class SearchKeywordRepositoryImpl @Inject constructor(
    private val dao: SearchKeywordDao
): SearchKeywordRepository{

    override suspend fun insertKeyWord(searchKeyword: String): Long =
        dao.insert(SearchKeywordEntity(search_keyword = searchKeyword))

    override suspend fun updateKeyword(searchKeyword: SearchKeyword): Int =
        dao.update(SearchKeywordMapper.mapperToEntity(searchKeyword))

    override suspend fun deleteKeyword(searchKeyword: SearchKeyword): Int =
        dao.update(SearchKeywordMapper.mapperToEntity(searchKeyword))

    override suspend fun getAll(): List<SearchKeyword> =
        dao.getAll().map { SearchKeywordMapper.mapperToModel(it) }

    override suspend fun getCurrentItems(): List<SearchKeyword> =
        dao.getCurrentItems().map { SearchKeywordMapper.mapperToModel(it) }

    override suspend fun isExistsKeyword(searchKeyword: String): Int =
        dao.isExistsKeyword(searchKeyword)
}