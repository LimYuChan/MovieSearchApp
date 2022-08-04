package com.devsurfer.domain.mapper

import com.devsurfer.domain.exception.NetworkException
import com.devsurfer.domain.state.Failure
import com.devsurfer.domain.utils.Constants
import java.lang.Exception

object NetworkExceptionMapper {
    fun mapperToFailure(exception: NetworkException): Failure{
        return when(exception){
            is NetworkException.IncorrectQueryException -> Failure.IncorrectQuery(exception.message ?: Constants.ERROR_MESSAGE_UNHANDLED)
            is NetworkException.InvalidSearchApiException -> Failure.InvalidSearchApi(exception.message ?: Constants.ERROR_MESSAGE_UNHANDLED)
            is NetworkException.SearchSystemErrorException -> Failure.SearchSystemError(exception.message ?: Constants.ERROR_MESSAGE_UNHANDLED)
        }
    }
}