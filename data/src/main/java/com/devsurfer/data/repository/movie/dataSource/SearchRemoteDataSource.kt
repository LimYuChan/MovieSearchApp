package com.devsurfer.data.repository.movie.dataSource

import com.devsurfer.data.mapper.movie.MovieMapper
import com.devsurfer.data.service.SearchService
import com.devsurfer.domain.model.movie.Movie
import javax.inject.Inject

class SearchRemoteDataSource @Inject constructor(
    private val service: SearchService
){
    suspend fun searchMovie(query: String, start: Int, display: Int): List<Movie>{
        val response = service.searchMovie(query, start, display)
        return response.items?.map { MovieMapper.mapperToModel(it) } ?: emptyList()
    }
}