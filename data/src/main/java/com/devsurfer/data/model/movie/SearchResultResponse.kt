package com.devsurfer.data.model.movie

data class SearchResultResponse(
    val total: Int = 0,
    val start: Int = 0,
    val display: Int = 0,
    val items: List<MovieResponse>? = emptyList()
)
