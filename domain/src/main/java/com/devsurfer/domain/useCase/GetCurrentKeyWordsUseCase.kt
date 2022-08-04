package com.devsurfer.domain.useCase

import android.util.Log
import com.devsurfer.domain.model.searchKeyword.SearchKeyword
import com.devsurfer.domain.repository.searchKeyword.SearchKeywordRepository
import com.devsurfer.domain.state.Failure
import com.devsurfer.domain.state.ResourceState
import com.devsurfer.domain.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCurrentKeyWordsUseCase @Inject constructor(
    private val repository: SearchKeywordRepository
){
    operator fun invoke(): Flow<ResourceState<List<SearchKeyword>>> = flow {
        //원래는 Exception 별로 다른 UI를 처리 할 예정이지만 해당 프로젝트에서는 Toast 메시지만 보여줘도 될 것 같아서 에러 처리 통합
        try{
            val keywordList = repository.getCurrentItems()
            emit(ResourceState.Success(keywordList))
        }catch (t: Throwable){
            Log.d(TAG, "invoke error: ${t.localizedMessage}")
            emit(ResourceState.Error(failure = Failure.UnHandle(Constants.ERROR_MESSAGE_SQL_GET_QUERY)))
        }
    }

    companion object{
        private const val TAG = "CurrentKeyWordsUseCase"
    }
}