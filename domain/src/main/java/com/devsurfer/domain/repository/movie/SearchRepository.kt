package com.devsurfer.domain.repository.movie

import com.devsurfer.domain.model.movie.Movie

interface SearchRepository {
    suspend fun searchMovie(query: String, start: Int, display: Int): List<Movie>
}