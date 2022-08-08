package com.devsurfer.domain.state

import com.devsurfer.domain.utils.Constants

sealed class Failure(val message: String){
    class IncorrectQuery(message: String): Failure(message)
    class InvalidSearchApi(message: String): Failure(message)
    class SearchSystemError(message: String): Failure(message)
    class NetworkNotConnected(message: String): Failure(message)
    class DatabaseIO(message: String): Failure(message)
    class UnHandle(message: String): Failure(message)
}
