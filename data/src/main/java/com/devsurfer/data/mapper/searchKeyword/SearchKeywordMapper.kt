package com.devsurfer.data.mapper.searchKeyword

import com.devsurfer.data.model.searchKeyword.SearchKeywordEntity
import com.devsurfer.domain.model.searchKeyword.SearchKeyword

//SearchKeyWord 객체는 내부 데이터 베이스에 저장되기 때문에 입출력시 data<->domain 으로 동작하도록 mapper를 작성했습니다.

fun SearchKeywordEntity.toModel(): SearchKeyword =
    SearchKeyword(
        id = this.id,
        searchKeyword = this.search_keyword
    )

fun SearchKeyword.toEntity(): SearchKeywordEntity =
    SearchKeywordEntity(
        id = this.id,
        search_keyword = this.searchKeyword
    )