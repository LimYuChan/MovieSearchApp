package com.devsurfer.domain.repository.movie

import androidx.paging.Pager
import androidx.paging.PagingData
import com.devsurfer.domain.model.movie.Movie

interface SearchRepository {
    suspend fun searchMovies(query: String): Pager<Int, Movie>
}