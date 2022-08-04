package com.devsurfer.data.mapper.searchKeyword

import com.devsurfer.data.model.searchKeyword.SearchKeywordEntity
import com.devsurfer.domain.model.searchKeyword.SearchKeyword

object SearchKeywordMapper {
    fun mapperToModel(entity: SearchKeywordEntity): SearchKeyword =
        SearchKeyword(
            id = entity.id,
            searchKeyword = entity.search_keyword
        )

    fun mapperToEntity(model: SearchKeyword): SearchKeywordEntity =
        SearchKeywordEntity(
            id = model.id,
            search_keyword = model.searchKeyword
        )
}