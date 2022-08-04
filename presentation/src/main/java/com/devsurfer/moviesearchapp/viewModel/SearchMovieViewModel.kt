package com.devsurfer.moviesearchapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devsurfer.domain.model.movie.Movie
import com.devsurfer.domain.state.ResourceState
import com.devsurfer.domain.useCase.SearchMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor(
    private val searchUseCase: SearchMovieUseCase
): ViewModel(){

    private val _searchResultState = Channel<ResourceState<List<Movie>>>()
    val searchResultState = _searchResultState.receiveAsFlow()

    private val _isLoadViewVisible = MutableLiveData(false)
    val isLoadViewVisible = _isLoadViewVisible

    fun searchMovie(query: String, start: Int, display: Int){
        searchUseCase.invoke(query, start, display).onStart {
            _isLoadViewVisible.value = true
        }.onEach {
            _searchResultState.send(it)
        }.onCompletion {
            _isLoadViewVisible.value = false
        }.launchIn(viewModelScope)
    }
}