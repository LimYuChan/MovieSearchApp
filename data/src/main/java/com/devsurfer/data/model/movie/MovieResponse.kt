package com.devsurfer.data.model.movie

data class MovieResponse(
    val title: String,
    val link: String? = null,
    val image: String? = null,
    val subtitle: String? = null,
    val pupDate: String? = null,
    val director: String? = null,
    val actor: String? = null,
    val userRating: String
)
