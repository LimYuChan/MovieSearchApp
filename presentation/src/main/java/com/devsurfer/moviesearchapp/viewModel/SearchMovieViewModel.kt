package com.devsurfer.moviesearchapp.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.devsurfer.domain.model.movie.Movie
import com.devsurfer.domain.useCase.InsertKeywordUseCase
import com.devsurfer.domain.useCase.SearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 영화를 검색하는 ViewModel 입니다.
 * 영화 검색시 최근 검색어도 업데이트 하게 됩니다.
 */

@HiltViewModel
class SearchMovieViewModel @Inject constructor(
    private val insertKeywordUseCase: InsertKeywordUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _pagingDataFlow = Channel<PagingData<Movie>>()
    val pagingDataFlow = _pagingDataFlow.receiveAsFlow()

    val lastKeyword = savedStateHandle.getLiveData<String>("keyword")

    fun searchMovie(query: String) {
        insertKeywordUseCase.invoke(query)
        CoroutineScope(Dispatchers.IO).launch {
            searchMoviesUseCase.invoke(query).flow.onEach {
                _pagingDataFlow.send(it)
            }.launchIn(viewModelScope)
        }
    }

    fun setLastKeyword(keyword: String){
        savedStateHandle["keyword"] = keyword
    }

    companion object {
        private const val TAG = "SearchMovieViewModel"
    }
}