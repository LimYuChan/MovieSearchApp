package com.devsurfer.domain.useCase

import androidx.paging.Pager
import androidx.paging.PagingData
import com.devsurfer.domain.model.movie.Movie
import com.devsurfer.domain.repository.movie.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

//영화를 검색하는 기능입니다.
//Google 문서에서 ViewModel에서 UI로 넘어갈 때 Flow로 넘기는걸 권장하고 있어서 UseCase에서도 Pager 객체를 전달합니다.
class SearchMoviesUseCase @Inject constructor(
    private val repository: SearchRepository
){
    suspend operator fun invoke(query: String): Pager<Int, Movie> = repository.searchMovies(query)
}