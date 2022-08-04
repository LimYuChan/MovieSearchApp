package com.devsurfer.domain.state

import com.devsurfer.domain.utils.Constants

sealed class Failure(val message: String){
    class IncorrectQuery(message: String): Failure(message)
    class InvalidSearchApi(message: String): Failure(message)
    class SearchSystemError(message: String): Failure(message)
    class NetworkNotConnected(message: String= Constants.ERROR_MESSAGE_INTERNET_CONNECTED): Failure(message)
    class UnHandle(message: String): Failure(message)
}
