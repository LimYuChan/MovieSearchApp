package com.devsurfer.domain.useCase

import androidx.paging.Pager
import androidx.paging.PagingData
import com.devsurfer.domain.model.movie.Movie
import com.devsurfer.domain.repository.movie.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val repository: SearchRepository
){
    suspend operator fun invoke(query: String): Pager<Int, Movie> = repository.searchMovies(query)
}