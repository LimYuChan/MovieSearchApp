package com.devsurfer.data.mapper.movie

import com.devsurfer.data.model.movie.SearchResultResponse
import com.devsurfer.domain.model.movie.SearchResult

//SearchResult 객체는 data -> domain으로만 작동하기 때문에 toModel mapper만 작성했습니다.
fun SearchResultResponse.toModel(): SearchResult =
    SearchResult(
        total = this.total,
        start = this.start,
        display = this.display,
        items = this.items?.map { it.toModel() }
    )