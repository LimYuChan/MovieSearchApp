package com.devsurfer.data.service

import com.devsurfer.data.model.movie.SearchResultResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchMovieService {
    @GET("movie.json")
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("start") start: Int,
        @Query("display") display: Int
    ): SearchResultResponse
}