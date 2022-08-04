package com.devsurfer.domain.model.movie

data class SearchResult(
    val total: Int = 0,
    val start: Int = 0,
    val display: Int = 0,
    val items: List<Movie>? = emptyList()
)
