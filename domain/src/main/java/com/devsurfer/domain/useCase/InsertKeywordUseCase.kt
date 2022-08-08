package com.devsurfer.domain.useCase

import android.util.Log
import com.devsurfer.domain.repository.searchKeyword.SearchKeywordRepository
import com.devsurfer.domain.state.Failure
import com.devsurfer.domain.state.ResourceState
import com.devsurfer.domain.utils.Constants
import kotlinx.coroutines.*
import javax.inject.Inject

//검색 시 최근 검색어를 같이 업데이트 해야하는데
//최근 검색한 10개의 키워드와 겹치면 추가하지 않도록 구성함
class InsertKeywordUseCase @Inject constructor(
    private val repository: SearchKeywordRepository
) {
    operator fun invoke(keyword: String){
        CoroutineScope(Dispatchers.IO).launch(coroutineExceptionHandler){
            val isExists = repository.isExistsKeyword(keyword) > 0
            if(!isExists){
                repository.insertKeyWord(keyword)
            }
        }
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler{_,exception ->
        //최근 검색어 저장하는 도중 발생하는 exception은 로그로 남기기만 해도 될 것 같아서 로그만 처리
        Log.d(TAG, "InsertKeywordUseCase Exception: ${exception.localizedMessage}")
    }

    companion object{
        private const val TAG = "InsertKeywordUseCase"
    }
}