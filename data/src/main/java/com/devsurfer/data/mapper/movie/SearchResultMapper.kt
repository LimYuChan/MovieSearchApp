package com.devsurfer.data.mapper.movie

import com.devsurfer.data.model.movie.SearchResultResponse
import com.devsurfer.domain.model.movie.SearchResult

object SearchResultMapper {
    fun mapperToModel(response: SearchResultResponse): SearchResult =
        SearchResult(
            total = response.total,
            start = response.start,
            display = response.display,
            items = response.items?.map { MovieMapper.mapperToModel(it) }
        )
}