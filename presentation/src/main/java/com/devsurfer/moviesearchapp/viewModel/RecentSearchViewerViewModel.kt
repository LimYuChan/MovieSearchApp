package com.devsurfer.moviesearchapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devsurfer.domain.model.movie.Movie
import com.devsurfer.domain.model.searchKeyword.SearchKeyword
import com.devsurfer.domain.state.ResourceState
import com.devsurfer.domain.useCase.GetCurrentKeyWordsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * 최근 검색 결과를 가져오는 ViewModel 입니다.
 */
@HiltViewModel
class RecentSearchViewerViewModel @Inject constructor(
    private val currentKeywordUseCase: GetCurrentKeyWordsUseCase
): ViewModel(){

    private val _currentKeywords = MutableSharedFlow<ResourceState<List<SearchKeyword>>>()
    val currentKeywords = _currentKeywords.asSharedFlow()

    private val _isLoadViewVisible = MutableLiveData(false)
    val isLoadViewVisible = _isLoadViewVisible

    fun getCurrentKeywords(){
        currentKeywordUseCase.invoke().onStart {
            _isLoadViewVisible.value = true
        }.onEach {
            _currentKeywords.emit(it)
        }.onCompletion {
            _isLoadViewVisible.value = false
        }.launchIn(viewModelScope)
    }
}