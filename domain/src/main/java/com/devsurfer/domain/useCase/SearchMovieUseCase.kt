package com.devsurfer.domain.useCase

import android.util.Log
import com.devsurfer.domain.exception.NetworkException
import com.devsurfer.domain.mapper.NetworkExceptionMapper
import com.devsurfer.domain.model.movie.Movie
import com.devsurfer.domain.repository.movie.SearchRepository
import com.devsurfer.domain.state.Failure
import com.devsurfer.domain.state.ResourceState
import com.devsurfer.domain.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    operator fun invoke(query: String, start: Int, display: Int): Flow<ResourceState<List<Movie>>> = flow{
        //원래는 Exception 별로 다른 UI를 처리 할 예정이지만 해당 프로젝트에서는 Toast 메시지만 보여줘도 될 것 같아서 에러 처리 통합
        try{
            val movieList = repository.searchMovie(query, start, display)
            emit(ResourceState.Success(movieList))
        }catch (e: IOException){
            emit(ResourceState.Error(failure = Failure.NetworkNotConnected()))
        }catch (e: NetworkException){
            emit(ResourceState.Error(failure = NetworkExceptionMapper.mapperToFailure(e)))
        }catch (t: Throwable){
            Log.d(TAG, "invoke throw error : ${t.localizedMessage}")
            emit(ResourceState.Error(failure = Failure.UnHandle(t.localizedMessage ?: Constants.ERROR_MESSAGE_UNHANDLED)))
        }
    }

    companion object{
        private const val TAG = "SearchMovieUseCase"
    }
}