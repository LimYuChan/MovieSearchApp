package com.devsurfer.data.repository.movie

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.devsurfer.data.repository.movie.dataSource.SearchMovieRemoteDataSource
import com.devsurfer.data.service.SearchService
import com.devsurfer.domain.model.movie.Movie
import com.devsurfer.domain.repository.movie.SearchRepository
import com.devsurfer.domain.utils.Constants
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val service: SearchService
): SearchRepository {

    override suspend fun searchMovies(query: String): Pager<Int, Movie> =
        Pager(
            config = PagingConfig(
                pageSize = Constants.NETWORK_PAGING_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                SearchMovieRemoteDataSource(service, query)
            }
        )
}