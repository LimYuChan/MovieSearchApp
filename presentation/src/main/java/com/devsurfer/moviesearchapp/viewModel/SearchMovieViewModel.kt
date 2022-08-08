package com.devsurfer.moviesearchapp.viewModel

import androidx.lifecycle.MutableLiveData
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

@HiltViewModel
class SearchMovieViewModel @Inject constructor(
    private val insertKeywordUseCase: InsertKeywordUseCase,
    private val searchPagingUseCase: SearchMoviesUseCase
): ViewModel(){

    private val _isLoadViewVisible = MutableLiveData(false)
    val isLoadViewVisible = _isLoadViewVisible

    private val _pagingDataFlow = Channel<PagingData<Movie>>()
    val pagingDataFlow = _pagingDataFlow.receiveAsFlow()

    fun searchMoviePaging(query: String){

        insertKeywordUseCase.invoke(query)

        CoroutineScope(Dispatchers.IO).launch {
            searchPagingUseCase.invoke(query).flow.onStart {
                _isLoadViewVisible.value = true
            }.onEach {
                _pagingDataFlow.send(it)
            }.onCompletion {
                _isLoadViewVisible.value = false
            }.launchIn(viewModelScope)
        }
    }

    companion object{
        private const val TAG = "SearchMovieViewModel"
    }
}