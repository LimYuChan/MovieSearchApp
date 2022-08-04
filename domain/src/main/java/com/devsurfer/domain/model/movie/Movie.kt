package com.devsurfer.domain.model.movie

data class Movie(
    val title: String,
    val link: String? = null,
    val image: String? = null,
    val subtitle: String? = null,
    val pupDate: String,
    val director: String? = null,
    val actor: String? = null,
    val userRating: String
)
