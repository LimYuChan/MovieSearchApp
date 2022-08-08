package com.devsurfer.domain.exception

import com.devsurfer.domain.utils.Constants

sealed class NetworkException(message: String): Throwable(message = message){
    class IncorrectQuery: NetworkException(message = Constants.ERROR_MESSAGE_INCORRECT_QUERY)
    class InvalidSearchApi: NetworkException(message = Constants.ERROR_MESSAGE_INVALID_SEARCH_API)
    class SearchSystemError: NetworkException(message = Constants.ERROR_MESSAGE_SEARCH_SYSTEM_ERROR)
    class NetworkNotConnected: NetworkException(message = Constants.ERROR_MESSAGE_NETWORK_CONNECTED)
}
