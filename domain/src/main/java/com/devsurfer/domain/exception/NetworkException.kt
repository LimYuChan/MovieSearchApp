package com.devsurfer.domain.exception

import com.devsurfer.domain.utils.Constants

sealed class NetworkException(message: String): Throwable(message = message){
    class IncorrectQueryException: NetworkException(message = Constants.ERROR_MESSAGE_INCORRECT_QUERY)
    class InvalidSearchApiException: NetworkException(message = Constants.ERROR_MESSAGE_INVALID_SEARCH_API)
    class SearchSystemErrorException: NetworkException(message = Constants.ERROR_MESSAGE_SEARCH_SYSTEM_ERROR)
}
