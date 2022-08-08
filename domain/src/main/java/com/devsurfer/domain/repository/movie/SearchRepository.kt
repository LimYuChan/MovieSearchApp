package com.devsurfer.domain.repository.movie

import androidx.paging.Pager
import androidx.paging.PagingData
import com.devsurfer.domain.model.movie.Movie

//추후 프로젝트에서 다른 검색을 할 수 있기 때문에
//SearchMovieRepository 가 아닌 SearchRepository 라고 설정하였습니다.
interface SearchRepository {
    suspend fun searchMovies(query: String): Pager<Int, Movie> //입력받은 텍스트로 영화 검색
}