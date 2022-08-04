package com.devsurfer.data.repository.movie

import com.devsurfer.data.repository.movie.dataSource.SearchRemoteDataSource
import com.devsurfer.domain.model.movie.Movie
import com.devsurfer.domain.repository.movie.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val remoteDataSource: SearchRemoteDataSource
): SearchRepository {
    override suspend fun searchMovie(query: String, start: Int, display: Int): List<Movie> =
        remoteDataSource.searchMovie(query, start, display)
}