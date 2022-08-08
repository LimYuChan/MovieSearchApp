package com.devsurfer.data.service

import com.devsurfer.data.model.movie.SearchResultResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * query = 검색어
 * start = 페이징 처리시 가져올 시작 점
 * display = 페이징 처리시 가져올 아이템 수
 */
interface SearchService {
    @GET("movie.json")
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("start") start: Int,
        @Query("display") display: Int
    ): SearchResultResponse
}