package com.devsurfer.domain.mapper

import com.devsurfer.domain.exception.NetworkException
import com.devsurfer.domain.state.Failure
import com.devsurfer.domain.utils.Constants

object NetworkExceptionMapper {
    fun mapperToFailure(exception: NetworkException): Failure{
        return when(exception){
            is NetworkException.IncorrectQuery -> Failure.IncorrectQuery(exception.message ?: Constants.ERROR_MESSAGE_UNHANDLED)
            is NetworkException.InvalidSearchApi -> Failure.InvalidSearchApi(exception.message ?: Constants.ERROR_MESSAGE_UNHANDLED)
            is NetworkException.SearchSystemError -> Failure.SearchSystemError(exception.message ?: Constants.ERROR_MESSAGE_UNHANDLED)
            is NetworkException.NetworkNotConnected -> Failure.NetworkNotConnected(exception.message ?: Constants.ERROR_MESSAGE_NETWORK_CONNECTED)
        }
    }
}